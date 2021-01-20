//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.cloud.gateway.filter;

import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import java.net.URI;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.config.HttpClientProperties;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter.Type;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.AbstractServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;
import reactor.netty.http.client.HttpClient.RequestSender;

public class NettyRoutingFilter implements GlobalFilter, Ordered {
    private static final Log log = LogFactory.getLog(NettyRoutingFilter.class);
    private final HttpClient httpClient;
    private final ObjectProvider<List<HttpHeadersFilter>> headersFiltersProvider;
    private final HttpClientProperties properties;
    private volatile List<HttpHeadersFilter> headersFilters;

    public NettyRoutingFilter(HttpClient httpClient, ObjectProvider<List<HttpHeadersFilter>> headersFiltersProvider, HttpClientProperties properties) {
        this.httpClient = httpClient;
        this.headersFiltersProvider = headersFiltersProvider;
        this.properties = properties;
    }

    public List<HttpHeadersFilter> getHeadersFilters() {
        if (this.headersFilters == null) {
            this.headersFilters = (List)this.headersFiltersProvider.getIfAvailable();
        }

        return this.headersFilters;
    }

    public int getOrder() {
        return 2147483647;
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI requestUrl = (URI)exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String scheme = requestUrl.getScheme();
        if (!ServerWebExchangeUtils.isAlreadyRouted(exchange) && ("http".equals(scheme) || "https".equals(scheme))) {
            ServerWebExchangeUtils.setAlreadyRouted(exchange);
            ServerHttpRequest request = exchange.getRequest();
            HttpMethod method = HttpMethod.valueOf(request.getMethodValue());
            String url = requestUrl.toASCIIString();
            HttpHeaders filtered = HttpHeadersFilter.filterRequest(this.getHeadersFilters(), exchange);
            DefaultHttpHeaders httpHeaders = new DefaultHttpHeaders();
            filtered.forEach(httpHeaders::set);
            boolean preserveHost = (Boolean)exchange.getAttributeOrDefault(ServerWebExchangeUtils.PRESERVE_HOST_HEADER_ATTRIBUTE, false);
            Flux<HttpClientResponse> responseFlux = ((RequestSender)this.httpClient.headers((headers) -> {
                headers.add(httpHeaders);
                if (preserveHost) {
                    String host = request.getHeaders().getFirst("Host");
                    headers.add("Host", host);
                } else {
                    headers.remove("Host");
                }

            }).request(method).uri(url)).send((req, nettyOutbound) -> {
//                if (log.isTraceEnabled()) {
//                    nettyOutbound.withConnection((connection) -> {
//                        log.trace("outbound route: " + connection.channel().id().asShortText() + ", inbound: " + exchange.getLogPrefix());
//                    });
//                }

                return nettyOutbound.send(request.getBody().map((dataBuffer) -> {
                    return ((NettyDataBuffer)dataBuffer).getNativeBuffer();
                }));
            }).responseConnection((res, connection) -> {
                exchange.getAttributes().put(ServerWebExchangeUtils.CLIENT_RESPONSE_ATTR, res);
                exchange.getAttributes().put(ServerWebExchangeUtils.CLIENT_RESPONSE_CONN_ATTR, connection);
                ServerHttpResponse response = exchange.getResponse();
                HttpHeaders headers = new HttpHeaders();
                res.responseHeaders().forEach((entry) -> {
                    headers.add((String)entry.getKey(), (String)entry.getValue());
                });
                String contentTypeValue = headers.getFirst("Content-Type");
                if (StringUtils.hasLength(contentTypeValue)) {
                    exchange.getAttributes().put("original_response_content_type", contentTypeValue);
                }

                HttpStatus status = HttpStatus.resolve(res.status().code());
                if (status != null) {
                    response.setStatusCode(status);
                } else {
                    if (!(response instanceof AbstractServerHttpResponse)) {
                        throw new IllegalStateException("Unable to set status code on response: " + res.status().code() + ", " + response.getClass());
                    }

                    ((AbstractServerHttpResponse)response).setStatusCode(HttpStatus.resolve(res.status().code()));
                }

                HttpHeaders filteredResponseHeaders = HttpHeadersFilter.filter(this.getHeadersFilters(), headers, exchange, Type.RESPONSE);
                if (!filteredResponseHeaders.containsKey("Transfer-Encoding") && filteredResponseHeaders.containsKey("Content-Length")) {
                    response.getHeaders().remove("Transfer-Encoding");
                }

                exchange.getAttributes().put(ServerWebExchangeUtils.CLIENT_RESPONSE_HEADER_NAMES, filteredResponseHeaders.keySet());
                response.getHeaders().putAll(filteredResponseHeaders);
                return Mono.just(res);
            });
            if (this.properties.getResponseTimeout() != null) {
                responseFlux = responseFlux.timeout(this.properties.getResponseTimeout(), Mono.error(new TimeoutException("Response took longer than timeout: " + this.properties.getResponseTimeout()))).onErrorMap(TimeoutException.class, (th) -> {
                    return new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, th.getMessage(), th);
                });
            }

            return responseFlux.then(chain.filter(exchange));
        } else {
            return chain.filter(exchange);
        }
    }
}
