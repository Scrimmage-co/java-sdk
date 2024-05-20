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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;

public class ScrimmageAPIService implements IAPIService {

  private static  ScrimmageAPIService INSTANCE;


  public static ScrimmageAPIService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ScrimmageAPIService(ScrimmageServiceFactory.config);
    }
    return INSTANCE;
  }
  private final ScrimmageConfigService scrimmageConfigService;
  private final Gson gson;


  private ScrimmageAPIService(ScrimmageConfigService scrimmageConfigService) {
    this.scrimmageConfigService = scrimmageConfigService;
    this.gson = new Gson();
  }

  @Override
  public RewardableEventDTO createIntegrationReward(Rewardable rewardable, String uniqueId,
      Reward reward) {
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
              ScrimmageApiServiceType.getUrl(scrimmageConfigService.getApiServerEndpoint()
                  , ScrimmageApiServiceType.API, "/integrations/rewards")))
          .POST(BodyPublishers.ofString(gson.toJson(rewardableEventDTO)))
          .header("Authorization", "Token " + scrimmageConfigService.getPrivateKey())
          .header("Scrimmage-Namespace", scrimmageConfigService.getNamespace())
          .header("Content-Type", "application/json")
          .build();
      HttpClient client = HttpClient.newHttpClient();
      response = client.send(request, HttpResponse.BodyHandlers.ofString());

    } catch (Exception ex) {
      throw new ScrimmageServiceUnavailable(
          String.format("%s is not available", ScrimmageApiServiceType.API));
    }
    return response == null ? null
        : gson.fromJson(response.body(),
            RewardableEventDTO.class);
  }

  @Override
  public TokenResponseDTO getUserToken(TokenOption options) {
    HttpResponse<String> response;
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(
              ScrimmageApiServiceType.getUrl(scrimmageConfigService.getApiServerEndpoint()
                  , ScrimmageApiServiceType.API, "/integrations/users")))
          .POST(BodyPublishers.ofString(gson.toJson(options)))
          .header("Authorization", "Token " + scrimmageConfigService.getPrivateKey())
          .header("Scrimmage-Namespace", scrimmageConfigService.getNamespace())
          .header("Content-Type", "application/json")
          .build();
      HttpClient client = HttpClient.newHttpClient();
      response = client.send(request, HttpResponse.BodyHandlers.ofString());

    } catch (Exception ex) {
      throw new ScrimmageServiceUnavailable(
          String.format("%s is not available", ScrimmageApiServiceType.API));
    }

    return response == null ? null
        : gson.fromJson(response.body(), TokenResponseDTO.class);
  }

  boolean getService(ScrimmageApiServiceType scrimmageApiServiceType) {

    HttpResponse<String> response;
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(
              ScrimmageApiServiceType.getUrl(scrimmageConfigService.getApiServerEndpoint()
                  , scrimmageApiServiceType, "/system/status")))
          .GET()
          .header("Authorization", "Token " + scrimmageConfigService.getPrivateKey())
          .header("Scrimmage-Namespace", scrimmageConfigService.getNamespace())
          .build();
      HttpClient client = HttpClient.newHttpClient();
      response = client.send(request, HttpResponse.BodyHandlers.ofString());

    } catch (Exception ex) {
      throw new ScrimmageServiceUnavailable(
          String.format("%s is not available", scrimmageApiServiceType));
    }

    return response != null && response.statusCode() == 200;
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
    HttpResponse<String> response;
    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(
              ScrimmageApiServiceType.getUrl(scrimmageConfigService.getApiServerEndpoint()
                  , ScrimmageApiServiceType.API, "/rewarders/keys/@me")))
          .GET()
          .header("Authorization", "Token " + scrimmageConfigService.getPrivateKey())
          .header("Scrimmage-Namespace", scrimmageConfigService.getNamespace())
          .build();
      HttpClient client = HttpClient.newHttpClient();
      response = client.send(request, HttpResponse.BodyHandlers.ofString());

    } catch (Exception ex) {
      throw new ScrimmageServiceUnavailable(
          String.format("%s is not available", ScrimmageApiServiceType.API));
    }
    return response != null && response.statusCode() == 200;
  }
}
