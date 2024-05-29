package com.scrimmage.core;

import com.scrimmage.common.dto.MyReward;
import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.ScrimmageApiServiceType;
import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;
import com.scrimmage.common.dto.UpTimeDTO;
import com.scrimmage.common.service.IAPIService;
import com.scrimmage.common.service.ILoggerService;

public class ScrimmageAPIService implements IAPIService {

  private final ILoggerService logger;

  private final ScrimmageConfig scrimmageConfig;

  public ScrimmageAPIService(ScrimmageConfig scrimmageConfig,
      ILoggerService logger) {
    this.scrimmageConfig = scrimmageConfig;
    this.logger = logger;
  }

  @Override
  public RewardableEventDTO createIntegrationReward(Rewardable rewardable, String uniqueId,
      Reward reward) {
        RewardableEventDTO rewardableEventDTO = RewardableEventDTO.builder()
            .userId(rewardable.getUserId())
            .eventId(uniqueId)
            .dataType(rewardable.getType())
            .body(reward)
            .build();
    HttpClientWithRetry<RewardableEventDTO, RewardableEventDTO> httpClient = new HttpClientWithRetry<>(
        this.scrimmageConfig, this.logger);
    RewardableEventDTO response = httpClient.exchange(ScrimmageApiServiceType.getUrl(
            scrimmageConfig.getApiServerEndpoint(),
            ScrimmageApiServiceType.API, "/integrations/rewards"), "POST", rewardableEventDTO,
        RewardableEventDTO.class);
    return response;

  }

  @Override
  public TokenResponseDTO getUserToken(TokenOption options) {
    HttpClientWithRetry<TokenOption, TokenResponseDTO> httpClient = new HttpClientWithRetry<>(
        this.scrimmageConfig, this.logger);
    return httpClient.exchange(ScrimmageApiServiceType.getUrl(
            scrimmageConfig.getApiServerEndpoint(),
            ScrimmageApiServiceType.API, "/integrations/users"), "POST", options,
        TokenResponseDTO.class);
  }

  boolean getService(ScrimmageApiServiceType scrimmageApiServiceType) {
    HttpClientWithRetry httpClient = new HttpClientWithRetry<>(
        this.scrimmageConfig, this.logger);
    httpClient.exchange(
        ScrimmageApiServiceType.getUrl(scrimmageConfig.getApiServerEndpoint(),
            scrimmageApiServiceType, "/system/status"), "GET", null,
        UpTimeDTO.class);
    return true;
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
    HttpClientWithRetry httpClient = new HttpClientWithRetry<>(
        this.scrimmageConfig, this.logger);
    httpClient.exchange(
        ScrimmageApiServiceType.getUrl(scrimmageConfig.getApiServerEndpoint(),
            ScrimmageApiServiceType.API, "/rewarders/keys/@me"), "GET",
        null,
        MyReward.class);
    return true;
  }

}
