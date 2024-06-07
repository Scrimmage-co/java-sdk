package com.scrimmage.core;

import com.google.gson.Gson;
import com.scrimmage.common.service.ILoggerService;
import com.scrimmage.common.service.RetryTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;

public class HttpClientWithRetry<B, R> extends RetryTemplate<B, R> {

    private final ScrimmageConfig scrimmageConfig;
    private final Gson gson;
    private final ILoggerService logger;

    public HttpClientWithRetry(ScrimmageConfig scrimmageConfig,
                               ILoggerService logger) {
        super(scrimmageConfig.getRetry(), logger);
        this.scrimmageConfig = scrimmageConfig;
        this.gson = new Gson();
        this.logger = logger;
    }

    public R exchange(String route, String httpMethod, B requestBody, Class<R> responseType) throws IOException, InterruptedException {
        Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(route))
                .header("Authorization", scrimmageConfig.getPrivateKey())
                .header("Scrimmage-Namespace", scrimmageConfig.getNamespace())
                .header("Content-Type", "application/json");
        if ("POST".equalsIgnoreCase(httpMethod)) {
            requestBuilder.POST(BodyPublishers.ofString(gson.toJson(requestBody)));
        } else if ("GET".equalsIgnoreCase(httpMethod)) {
            requestBuilder.GET();
        }
        HttpRequest request = requestBuilder.build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), responseType);
    }
}

