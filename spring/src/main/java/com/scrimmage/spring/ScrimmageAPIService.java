package com.scrimmage.spring;

import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.ScrimmageApiServiceType;
import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;
import com.scrimmage.common.exceptions.ScrimmageServiceUnavailable;
import com.scrimmage.common.service.IAPIService;
import com.scrimmage.common.service.ILoggerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ScrimmageAPIService implements IAPIService {

  private final ScrimmageConfig scrimmageConfig;
  private final ILoggerService logger;


  public ScrimmageAPIService(ScrimmageConfig scrimmageConfig, ILoggerService loggerService) {
    this.scrimmageConfig = scrimmageConfig;
    this.logger = loggerService;
  }

  @Override
  public RewardableEventDTO createIntegrationReward(Rewardable rewardable, String uniqueId,
      Reward reward) {
    int attempts = 0;
    do {
      try {
        RestTemplate restTemplate = new RestTemplate();
        String serviceUrl =
            ScrimmageApiServiceType.getUrl(this.scrimmageConfig.getApiServerEndpoint(),
                ScrimmageApiServiceType.API, "/integrations/rewards");

        RewardableEventDTO rewardableEventDTO = RewardableEventDTO.builder()
            .userId(rewardable.getUserId())
            .eventId(uniqueId)
            .dataType(rewardable.getType())
            .body(reward)
            .build();

        HttpEntity<RewardableEventDTO> entity = new HttpEntity<>(rewardableEventDTO,
            getHttpHeaders());

        ResponseEntity<RewardableEventDTO> response = restTemplate.exchange(
            serviceUrl,
            HttpMethod.POST,
            entity,
            RewardableEventDTO.class);

        return response.getBody();
      } catch (Exception ex) {
        attempts++;
        logger.error(
            String.format("%s/integrations/rewards is not available", ScrimmageApiServiceType.API));
      }
    } while (attempts <= scrimmageConfig.getRetry());
    throw new ScrimmageServiceUnavailable(
        String.format("%s/integrations/rewards is not available", ScrimmageApiServiceType.API));
  }

  @Override
  public TokenResponseDTO getUserToken(TokenOption options) {
    int attempts = 0;
    do {
      try {
        RestTemplate restTemplate = new RestTemplate();
        String serviceUrl =
            ScrimmageApiServiceType.getUrl(this.scrimmageConfig.getApiServerEndpoint(),
                ScrimmageApiServiceType.API, "/integrations/users");

        HttpEntity<TokenOption> entity = new HttpEntity<>(options, getHttpHeaders());

        ResponseEntity<TokenResponseDTO> response = restTemplate.exchange(
            serviceUrl,
            HttpMethod.POST,
            entity,
            TokenResponseDTO.class);

        return response.getBody();
      } catch (Exception ex) {
        attempts++;
        logger.error(
            String.format("%s/integrations/rewards is not available", ScrimmageApiServiceType.API));
      }
    } while (attempts <= scrimmageConfig.getRetry());
    throw new ScrimmageServiceUnavailable(
        String.format("%s/integrations/rewards is not available", ScrimmageApiServiceType.API));
  }

  boolean getService(ScrimmageApiServiceType scrimmageApiServiceType) {
    int attempts = 0;
    do {
      try {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(
            ScrimmageApiServiceType.getUrl(this.scrimmageConfig.getApiServerEndpoint(),
                scrimmageApiServiceType, "/system/status"), Object.class);
        return forEntity.getStatusCode() == HttpStatus.OK;
      } catch (Exception ex) {
        attempts++;
        logger.error(
            String.format("%s/integrations/rewards is not available", scrimmageApiServiceType));
      }
    } while (attempts <= scrimmageConfig.getRetry());
    throw new ScrimmageServiceUnavailable(
        String.format("%s/integrations/rewards is not available", scrimmageApiServiceType));
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
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> entity = new HttpEntity<>(getHttpHeaders());
    ResponseEntity<Object> response = restTemplate.exchange(
        ScrimmageApiServiceType.getUrl(this.scrimmageConfig.getApiServerEndpoint(),
            ScrimmageApiServiceType.API, "/rewarders/keys/@me"),
        HttpMethod.GET,
        entity,
        Object.class);
    return response.getStatusCode() == HttpStatus.OK;

  }

  private HttpHeaders getHttpHeaders() {
    int attempts = 0;
    do {
      try {
        String privateKey = this.scrimmageConfig.getPrivateKey();
        String namespace = this.scrimmageConfig.getNamespace();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Token " + privateKey);
    headers.set("Scrimmage-Namespace", namespace);
    return headers;
      } catch (Exception ex) {
        attempts++;
        logger.error(
            String.format("%s/integrations/rewards is not available", ScrimmageApiServiceType.API));
      }
    } while (attempts <= scrimmageConfig.getRetry());
    throw new ScrimmageServiceUnavailable(
        String.format("%s/integrations/rewards is not available", ScrimmageApiServiceType.API));
  }
}
