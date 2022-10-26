<<<<<<<< HEAD:bachelor-up/bachelor-up-rsclient/src/main/java/cn/org/bachelor/up/oauth2/rsclient/URLConnectionClient.java
package cn.org.bachelor.up.oauth2.rsclient;
========
package cn.org.bachelor.iam.oauth2.client;
>>>>>>>> 1f197210624ca12e365eeb3c8dea8f66ce884393:bachelor-iam/bachelor-oauth2/bachelor-oauth2-common/src/main/java/cn/org/bachelor/iam/oauth2/client/URLConnectionClient.java


import cn.org.bachelor.iam.oauth2.OAuthConstant;
import cn.org.bachelor.iam.oauth2.exception.OAuthBusinessException;
import cn.org.bachelor.iam.oauth2.exception.OAuthSystemException;
import cn.org.bachelor.iam.oauth2.utils.OAuthUtils;
import cn.org.bachelor.iam.oauth2.request.DefaultOAuthRequest;
import cn.org.bachelor.iam.oauth2.response.OAuthResponse;
import cn.org.bachelor.iam.oauth2.response.OAuthResponseFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by team bachelor on 15/5/20.
 */
public class URLConnectionClient implements HttpClient {

    public URLConnectionClient() {
    }

    @Override
    public <T extends OAuthResponse> T execute(DefaultOAuthRequest request, Map<String, String> headers,
                                               String requestMethod, Class<T> responseClass)
            throws OAuthSystemException, OAuthBusinessException {

        String responseBody = null;
        URLConnection c = null;
        int responseCode = 0;
        try {
            URL url = new URL(request.getLocationUri());

            c = url.openConnection();
            responseCode = -1;
            if (c instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection)c;

                if (headers != null && !headers.isEmpty()) {
                    for (Map.Entry<String, String> header : headers.entrySet()) {
                        httpURLConnection.addRequestProperty(header.getKey(), header.getValue());
                    }
                }

                if (request.getHeaders() != null) {
                    for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                        httpURLConnection.addRequestProperty(header.getKey(), header.getValue());
                    }
                }

                if (!OAuthUtils.isEmpty(requestMethod)) {
                    httpURLConnection.setRequestMethod(requestMethod);
                    if (requestMethod.equals(OAuthConstant.HttpMethod.POST)) {
                        httpURLConnection.setDoOutput(true);
                        OutputStream ost = httpURLConnection.getOutputStream();
                        PrintWriter pw = new PrintWriter(ost);
                        pw.print(request.getBody());
                        pw.flush();
                        pw.close();
                    }
                } else {
                    httpURLConnection.setRequestMethod(OAuthConstant.HttpMethod.GET);
                }

                httpURLConnection.connect();

                InputStream inputStream;
                responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 400 || responseCode == 401) {
                    inputStream = httpURLConnection.getErrorStream();
                } else {
                    inputStream = httpURLConnection.getInputStream();
                }

                responseBody = OAuthUtils.streamToString(inputStream);
            }
        } catch (IOException e) {
        	e.printStackTrace();
            throw new OAuthSystemException(e);
        }

        return OAuthResponseFactory
                .createCustomResponse(responseBody, c.getContentType(), responseCode, responseClass);
    }

    @Override
    public void shutdown() {
        // Nothing to do here
    }

}
