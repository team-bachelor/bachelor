package org.springframework.cloud.gateway.filter;


import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter.Type;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.AbstractServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.NettyPipeline.SendOptions;
import reactor.ipc.netty.http.client.HttpClient;
import reactor.ipc.netty.http.client.HttpClientRequest;

import java.net.URI;
import java.util.List;

public class NettyRoutingFilter implements GlobalFilter, Ordered {
    private final HttpClient httpClient;
    private final ObjectProvider<List<HttpHeadersFilter>> headersFilters;

    public NettyRoutingFilter(HttpClient httpClient, ObjectProvider<List<HttpHeadersFilter>> headersFilters) {
        this.httpClient = httpClient;
        this.headersFilters = headersFilters;
    }

    public int getOrder() {
        return 2147483647;
    }

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI requestUrl = (URI) exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        String scheme = requestUrl.getScheme();
        if (!ServerWebExchangeUtils.isAlreadyRouted(exchange) && ("http".equals(scheme) || "https".equals(scheme))) {
            ServerWebExchangeUtils.setAlreadyRouted(exchange);
            ServerHttpRequest request = exchange.getRequest();
            HttpMethod method = HttpMethod.valueOf(request.getMethod().toString());
            String url = requestUrl.toString();
            HttpHeaders filtered = HttpHeadersFilter.filterRequest((List) this.headersFilters.getIfAvailable(), exchange);
            DefaultHttpHeaders httpHeaders = new DefaultHttpHeaders();
            filtered.forEach(httpHeaders::set);
            String transferEncoding = request.getHeaders().getFirst("Transfer-Encoding");
            boolean chunkedTransfer = "chunked".equalsIgnoreCase(transferEncoding);
            boolean preserveHost = (Boolean) exchange.getAttributeOrDefault(ServerWebExchangeUtils.PRESERVE_HOST_HEADER_ATTRIBUTE, false);
            return this.httpClient.request(method, url, (req) -> {
                HttpClientRequest proxyRequest = req.options(SendOptions::flushOnEach).headers(httpHeaders).chunkedTransfer(chunkedTransfer).failOnServerError(false).failOnClientError(false);
                if (preserveHost) {
                    String host = request.getHeaders().getFirst("Host");
                    proxyRequest.header("Host", host);
                }

                return proxyRequest.sendHeaders().send(request.getBody().map((dataBuffer) -> {
                    return ((NettyDataBuffer) dataBuffer).getNativeBuffer();
                }));
            }).doOnNext((res) -> {
                ServerHttpResponse response = exchange.getResponse();
                HttpHeaders headers = new HttpHeaders();
                res.responseHeaders().forEach((entry) -> {
                    headers.add((String) entry.getKey(), (String) entry.getValue());
                });
                if (headers.getContentType() != null) {
                    exchange.getAttributes().put("original_response_content_type", headers.getContentType());
                }
                HttpHeaders filteredResponseHeaders = HttpHeadersFilter.filter((List) this.headersFilters.getIfAvailable(), headers, exchange, Type.RESPONSE);
                response.getHeaders().putAll(filteredResponseHeaders);
                HttpStatus status = HttpStatus.resolve(res.status().code());
                if (status != null) {
                    response.setStatusCode(status);
                } else {
                    if (!(response instanceof AbstractServerHttpResponse)) {
                        throw new IllegalStateException("Unable to set status code on response: " + res.status().code() + ", " + response.getClass());
                    }

                    ((AbstractServerHttpResponse) response).setStatusCodeValue(res.status().code());
                }

                exchange.getAttributes().put(ServerWebExchangeUtils.CLIENT_RESPONSE_ATTR, res);
            }).then(chain.filter(exchange));
        } else {
            return chain.filter(exchange);
        }
    }
}
