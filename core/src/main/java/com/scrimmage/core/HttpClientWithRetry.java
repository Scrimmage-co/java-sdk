package com.scrimmage.core;

import com.google.gson.Gson;
import com.scrimmage.common.exceptions.ScrimmageServiceUnavailable;
import com.scrimmage.common.helper.ExponentialTimeHelper;
import com.scrimmage.common.service.ILoggerService;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;

public class HttpClientWithRetry<B, R> {

  private ScrimmageConfig scrimmageConfig;
  private Gson gson;
  private ILoggerService logger;

  public HttpClientWithRetry(ScrimmageConfig scrimmageConfig,
      ILoggerService logger) {
    this.scrimmageConfig = scrimmageConfig;
    this.gson = new Gson();
    this.logger = logger;
  }

  public R exchange(String route, String httpMethod, B requestBody, Class<R> responseType) {
    int attempt = 0;
    do {
      try {
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
      } catch (Exception ex) {
        logger.error("Error while requesting %s", route, ex);
      }
      if (attempt < scrimmageConfig.getRetry()) {
        attempt++;
        try {
          Thread.sleep(ExponentialTimeHelper.getExponentialTime(attempt));
          logger.info("Retrying... Attempt " + attempt);

        } catch (Exception ex) {
          logger.error("Error sleeping", ex);
        }
      } else {
        break;
      }
    } while (true);
    throw new ScrimmageServiceUnavailable(
        String.format("%s is not available", route));
  }
}

