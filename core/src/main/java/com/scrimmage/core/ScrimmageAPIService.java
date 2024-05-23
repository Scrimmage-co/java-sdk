package com.scrimmage.core;

import com.google.gson.Gson;
import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.ScrimmageApiServiceType;
import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;
import com.scrimmage.common.exceptions.ScrimmageServiceUnavailable;
import com.scrimmage.common.service.IAPIService;
import com.scrimmage.common.service.ILoggerService;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

public class ScrimmageAPIService implements IAPIService {

  private final ILoggerService logger;

  private final ScrimmageConfig scrimmageConfig;
  private final Gson gson;

  public ScrimmageAPIService(ScrimmageConfig scrimmageConfig,
      ILoggerService logger) {
    this.scrimmageConfig = scrimmageConfig;
    this.logger = logger;
    this.gson = new Gson();
  }

  @Override
  public RewardableEventDTO createIntegrationReward(Rewardable rewardable, String uniqueId,
      Reward reward) {
    int attempt = 0;
    do {
      HttpResponse<String> response;
      try {
        RewardableEventDTO rewardableEventDTO = RewardableEventDTO.builder()
            .userId(rewardable.getUserId())
            .eventId(uniqueId)
            .dataType(rewardable.getType())
            .body(reward)
            .build();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(
                ScrimmageApiServiceType.getUrl(scrimmageConfig.getApiServerEndpoint()
                    , ScrimmageApiServiceType.API, "/integrations/rewards")))
            .POST(BodyPublishers.ofString(gson.toJson(rewardableEventDTO)))
            .header("Authorization", "Token " + scrimmageConfig.getPrivateKey())
            .header("Scrimmage-Namespace", scrimmageConfig.getNamespace())
            .header("Content-Type", "application/json")
            .build();
        HttpClient client = HttpClient.newHttpClient();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response == null ? null
            : gson.fromJson(response.body(),
                RewardableEventDTO.class);
      } catch (Exception ex) {
        logger.error(
            String.format("%s/integrations/rewards is not available", ScrimmageApiServiceType.API));
      }

      attempt++;
      try {
        Thread.sleep(2000L);
        logger.info("Retrying... Attempt " + (attempt + 1));

      } catch (Exception ex) {
        logger.error("Error sleeping", ex);
      }
    } while (attempt <= scrimmageConfig.getRetry());
    throw new ScrimmageServiceUnavailable(
        String.format("%s/integrations/rewards is not available", ScrimmageApiServiceType.API));
  }

  @Override
  public TokenResponseDTO getUserToken(TokenOption options) {
    HttpResponse<String> response;
    int attempt = 0;
    do {
      try {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(
                ScrimmageApiServiceType.getUrl(scrimmageConfig.getApiServerEndpoint()
                    , ScrimmageApiServiceType.API, "/integrations/users")))
            .POST(BodyPublishers.ofString(gson.toJson(options)))
            .header("Authorization", "Token " + scrimmageConfig.getPrivateKey())
            .header("Scrimmage-Namespace", scrimmageConfig.getNamespace())
            .header("Content-Type", "application/json")
            .build();
        HttpClient client = HttpClient.newHttpClient();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response == null ? null
            : gson.fromJson(response.body(), TokenResponseDTO.class);
      } catch (Exception ex) {
        logger.error(
            String.format("%s is not available", ScrimmageApiServiceType.API));
      }
      attempt++;
      try {
        Thread.sleep(2000L);
        logger.info("Retrying... Attempt " + (attempt + 1));

      } catch (Exception ex) {
        logger.error("Error sleeping", ex);
      }
    } while (attempt <= scrimmageConfig.getRetry());
    throw new ScrimmageServiceUnavailable(
        String.format("%s/integrations/rewards is not available", ScrimmageApiServiceType.API));
  }

  boolean getService(ScrimmageApiServiceType scrimmageApiServiceType) {

    HttpResponse<String> response;
    int attempt = 0;
    do {
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(
              ScrimmageApiServiceType.getUrl(scrimmageConfig.getApiServerEndpoint()
                  , scrimmageApiServiceType, "/system/status")))
          .GET()
          .header("Authorization", "Token " + scrimmageConfig.getPrivateKey())
          .header("Scrimmage-Namespace", scrimmageConfig.getNamespace())
          .build();
      HttpClient client = HttpClient.newHttpClient();
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response != null && response.statusCode() == 200;
    } catch (Exception ex) {
      logger.error(
          String.format("%s is not available", scrimmageApiServiceType));
    }
      attempt++;
      try {
        Thread.sleep(2000L);
        logger.info("Retrying... Attempt " + (attempt + 1));

      } catch (Exception ex) {
        logger.error("Error sleeping", ex);
      }
    } while (attempt <= scrimmageConfig.getRetry());
    throw new ScrimmageServiceUnavailable(
        String.format("%s/system/status is not available", scrimmageApiServiceType));
  }

  @Override
  public boolean getOverallServiceStatus() {
    boolean active = true;
    for (ScrimmageApiServiceType v : ScrimmageApiServiceType.values()) {
      active = getService(v);
      if (!active) {
        break;
      }
    }
    return active;
  }

  @Override
  public boolean getRewarderKeyDetails() {
    int attempt = 1;
    do {
      HttpResponse<String> response;
      try {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(
                ScrimmageApiServiceType.getUrl(scrimmageConfig.getApiServerEndpoint()
                    , ScrimmageApiServiceType.API, "/rewarders/keys/@me")))
            .GET()
            .header("Authorization", "Token " + scrimmageConfig.getPrivateKey())
            .header("Scrimmage-Namespace", scrimmageConfig.getNamespace())
            .build();
        HttpClient client = HttpClient.newHttpClient();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response != null && response.statusCode() == 200;

      } catch (Exception ex) {
        logger.info(
            String.format("%s is not available", ScrimmageApiServiceType.API));
      }
      attempt++;
      try {
        Thread.sleep(2000L);
        logger.info("Retrying... Attempt " + (attempt + 1));

      } catch (Exception ex) {
        logger.error("Error sleeping", ex);
      }
    } while (attempt <= scrimmageConfig.getRetry());
    throw new ScrimmageServiceUnavailable(
        String.format("%s/rewarders/keys/@me is not available", ScrimmageApiServiceType.API));
  }
}
