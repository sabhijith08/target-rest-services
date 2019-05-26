package com.redsky.client.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.curator.utils.CloseableUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpClientManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientManager.class);

    @Value("${httpconfig.maxHttpConnections}")
    private transient Integer maxHttpConnections;

    @Value("${httpconfig.maxHttpConnectionsPerRoute}")
    private transient Integer maxHttpConnectionsPerRoute;

    @Value("${httpconfig.connectionTimeout}")
    private transient Integer connectionTimeout;

    @Value("${httpconfig.connectionRequestTimeout}")
    private transient Integer connectionRequestTimeout;

    @Value("${httpconfig.socketTimeout}")
    private transient Integer socketTimeout;

    @Value("${httpconfig.maxHttpRetries}")
    private transient Integer maxHttpRetries;

    CloseableHttpClient client = null;

    @PostConstruct
    public void init() {

        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxHttpConnections);
        connectionManager.setDefaultMaxPerRoute(maxHttpConnectionsPerRoute);

        final RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectionTimeout * 1000)
                .setConnectionRequestTimeout(connectionRequestTimeout * 1000).setSocketTimeout(socketTimeout * 1000)
                .build();

        client = HttpClients.custom().setRetryHandler(retryHandler).setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager).build();
    }

    public String doHttpGet(final String url, final ResponseHandler<? extends Object> handler,
            final Map<String, String> headers) throws IOException {

        final HttpClientContext context = HttpClientContext.create();
        final HttpGet httpGet = new HttpGet(url);

        LOGGER.debug("Executing request {}", httpGet.getRequestLine());
        if (headers != null && headers.size() > 0) {
            for (final String key : headers.keySet()) {
                httpGet.addHeader(key, headers.get(key));
            }
        }
        if (client == null) {
            LOGGER.info("Re-initializing the http-client");
            client = httpClient(true);
        }

        final String responseBody = (String) client.execute(httpGet, handler, context);
        LOGGER.debug("Server Response: {}", responseBody);
        return responseBody;
    }

    public String doHttpDelete(final String url) throws IOException {

        CloseableHttpResponse httpResponse = null;

        try {
            final HttpClientContext context = HttpClientContext.create();
            final HttpDelete httpDelete = new HttpDelete(url);
            LOGGER.debug("Executing Delete request {}", httpDelete.getRequestLine());

            if (client == null) {
                LOGGER.info("Re-initializing the http-client");
                client = httpClient(true);
            }
            httpResponse = client.execute(httpDelete, context);
            final String content = EntityUtils.toString(httpResponse.getEntity());

            final StatusLine statusLine = httpResponse.getStatusLine();

            LOGGER.debug("Server Response for delete: {}", statusLine, content);

            final String response = String.valueOf(statusLine.getStatusCode());

            return response;
        } finally {
            CloseableUtils.closeQuietly(httpResponse);
        }
    }

    public CloseableHttpResponse doHttpGet(final String uri, final Header[] headers) throws Exception {
        CloseableHttpResponse closeableresponse = null;
        final HttpGet httpget = new HttpGet(uri);
        httpget.setHeaders(headers);
        if (client == null) {
            LOGGER.info("Re-initializing the http-client");
            client = httpClient(true);
        }
        closeableresponse = client.execute(httpget);
        LOGGER.debug("Response Status line :" + closeableresponse.toString());
        LOGGER.debug("HTTP status " + closeableresponse.getStatusLine().getStatusCode());
        if (closeableresponse.getStatusLine().getStatusCode() == 202
                || closeableresponse.getStatusLine().getStatusCode() == 200) {

            return closeableresponse;
        } else {
            throw new Exception("cannot get records using anchor id and query. status code: "
                    + closeableresponse.getStatusLine().getStatusCode());
        }
    }

    public String doHttpPost(final String url, final String payload, final Header[] headers,
            final ResponseHandler<String> handler) throws IOException {

        final HttpClientContext context = HttpClientContext.create();
        final HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(payload));
        post.setHeaders(headers);
        LOGGER.debug("Executing request {} with payload {}", post.getRequestLine(), payload);

        if (client == null) {
            LOGGER.info("Re-initializing the http-client");
            client = httpClient(true);
        }
        final String responseBody = client.execute(post, handler, context);
        LOGGER.debug("Server Response: {}", responseBody);

        return responseBody;
    }

    public String doHttpPut(final String url, final String payload, final Header[] headers,
            final ResponseHandler<String> handler) throws IOException {

        final HttpClientContext context = HttpClientContext.create();
        final HttpPut post = new HttpPut(url);
        post.setEntity(new StringEntity(payload));
        post.setHeaders(headers);
        LOGGER.debug("Executing request {} with payload {}", post.getRequestLine(), payload);
        if (client == null) {
            LOGGER.info("Re-initializing the http-client");
            client = httpClient(true);
        }
        final String responseBody = client.execute(post, handler, context);
        LOGGER.debug("Server Response: {}", responseBody);

        return responseBody;
    }

    public String doHttpPut(final String url, final String payload, final Map<String, String> headers,
            final ResponseHandler<String> handler) throws IOException {

        final HttpClientContext context = HttpClientContext.create();
        final HttpPut put = new HttpPut(url);
        put.setEntity(new StringEntity(payload));

        if (headers != null && headers.size() > 0) {
            for (final String key : headers.keySet()) {
                put.addHeader(key, headers.get(key));
            }
        }

        LOGGER.debug("Executing request {} with payload {}", put.getRequestLine(), payload);
        if (client == null) {
            LOGGER.info("Re-initializing the http-client");
            client = httpClient(true);
        }
        final String responseBody = client.execute(put, handler, context);
        LOGGER.debug("Server Response: {}", responseBody);

        return responseBody;
    }

    public String doHttpPost(final String url, final String payload, final Map<String, String> headers,
            final ResponseHandler<String> handler) throws IOException {

        final HttpClientContext context = HttpClientContext.create();
        final HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(payload));

        if (headers != null && headers.size() > 0) {
            for (final String key : headers.keySet()) {
                post.addHeader(key, headers.get(key));
            }
        }

        LOGGER.debug("Executing request {} with payload {}", post.getRequestLine(), payload);

        if (client == null) {
            LOGGER.info("Re-initializing the http-client");
            client = httpClient(true);
        }
        final String responseBody = client.execute(post, handler, context);
        LOGGER.debug("Server Response: {}", responseBody);

        return responseBody;
    }

    public String doHttpPut(final String url, final byte[] payload, final Map<String, String> headers,
            final ResponseHandler<String> handler) throws IOException {

        final HttpClientContext context = HttpClientContext.create();
        final HttpPut put = new HttpPut(url);
        put.setEntity(new ByteArrayEntity(payload));

        if (headers != null && headers.size() > 0) {
            for (final String key : headers.keySet()) {
                put.addHeader(key, headers.get(key));
            }
        }

        LOGGER.debug("Executing request {}", put.getRequestLine());
        if (client == null) {
            LOGGER.info("Re-initializing the http-client");
            client = httpClient(true);
        }
        final String responseBody = client.execute(put, handler, context);
        LOGGER.debug("Server Response: {}", responseBody);

        return responseBody;
    }

    private CloseableHttpClient httpClient(final boolean useRetryHandler) {

        HttpClientBuilder builder = HttpClientBuilder.create();

        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxHttpConnections);
        connectionManager.setDefaultMaxPerRoute(maxHttpConnectionsPerRoute);

        if (useRetryHandler) {
            builder = builder.setRetryHandler(retryHandler);
        }
        final RequestConfig config = RequestConfig.custom().setConnectTimeout(connectionTimeout * 1000)
                .setConnectionRequestTimeout(connectionRequestTimeout * 1000).setSocketTimeout(socketTimeout * 1000)
                .build();
        final CloseableHttpClient httpClient = builder.setDefaultRequestConfig(config)
                .setConnectionManager(connectionManager).build();

        return httpClient;
    }

    private final HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {

        @Override
        public boolean retryRequest(final IOException exception, final int executionCount, final HttpContext context) {
            LOGGER.warn(
                    "Exception occoured while performing REST operation. Retrying the operation {} times for every 5 seconds and execution count is {}/{}  ",
                    maxHttpRetries, executionCount, maxHttpRetries);
            try {
                Thread.sleep(5000);
            } catch (final Exception e) {
                LOGGER.error("Thread interrupted occured while waiting for next iteration ", e);
            }

            // Do not retry if over max retry count
            if (executionCount >= maxHttpRetries) { return false; }

            // Timeout
            if (exception instanceof InterruptedIOException || exception instanceof UnknownHostException
                    || exception instanceof ConnectTimeoutException || exception instanceof HttpHostConnectException
                    || exception instanceof SSLException) {

            return false; }

            final HttpClientContext clientContext = HttpClientContext.adapt(context);
            final HttpRequest request = clientContext.getRequest();
            final boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            LOGGER.info("idempotent {}", idempotent);
            // Retry if the request is considered idempotent
            if (idempotent) { return true; }
            return false;
        }

    };

    public void shutdown() {
        LOGGER.info("Shutting down http connection manager.");
        CloseableUtils.closeQuietly(client);
    }

}
