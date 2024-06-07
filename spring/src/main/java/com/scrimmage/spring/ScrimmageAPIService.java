package com.scrimmage.spring;

import com.scrimmage.common.dto.*;
import com.scrimmage.common.service.IAPIService;
import com.scrimmage.common.service.ILoggerService;
import org.springframework.http.HttpMethod;

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
        RewardableEventDTO rewardableEventDTO = RewardableEventDTO.builder()
                .userId(rewardable.getUserId())
                .eventId(uniqueId)
                .dataType(rewardable.getType())
                .body(reward)
                .build();

        RestTemplateWithRetry<RewardableEventDTO, RewardableEventDTO> restTemplate =
                new RestTemplateWithRetry<RewardableEventDTO, RewardableEventDTO>(scrimmageConfig,
                        logger);
        String serviceUrl =
                ScrimmageApiServiceType.getUrl(this.scrimmageConfig.getApiServerEndpoint(),
                        ScrimmageApiServiceType.API, "/integrations/rewards");

        return restTemplate.exchangeWithRetry(
                serviceUrl,
                HttpMethod.POST.name(),
                rewardableEventDTO,
                RewardableEventDTO.class);
    }


    @Override
    public TokenResponseDTO getUserToken(TokenOption options) {
        RestTemplateWithRetry<TokenOption, TokenResponseDTO> restTemplate =
                new RestTemplateWithRetry<TokenOption, TokenResponseDTO>(scrimmageConfig,
                        logger);
        String serviceUrl =
                ScrimmageApiServiceType.getUrl(this.scrimmageConfig.getApiServerEndpoint(),
                        ScrimmageApiServiceType.API, "/integrations/users");

        return restTemplate.exchangeWithRetry(
                serviceUrl,
                HttpMethod.POST.name(),
                options,
                TokenResponseDTO.class);
    }

    boolean getService(ScrimmageApiServiceType scrimmageApiServiceType) {
        RestTemplateWithRetry restTemplate =
                new RestTemplateWithRetry(scrimmageConfig, logger);
        String serviceUrl =
                ScrimmageApiServiceType.getUrl(this.scrimmageConfig.getApiServerEndpoint(),
                        scrimmageApiServiceType, "/system/status");
        restTemplate.exchangeWithRetry(
                serviceUrl,
                HttpMethod.GET.name(),
                null,
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
        RestTemplateWithRetry restTemplate =
                new RestTemplateWithRetry(scrimmageConfig, logger);
        String serviceUrl =
                ScrimmageApiServiceType.getUrl(this.scrimmageConfig.getApiServerEndpoint(),
                        ScrimmageApiServiceType.API, "/rewarders/keys/@me");
        restTemplate.exchangeWithRetry(
                serviceUrl,
                HttpMethod.GET.name(),
                null,
                String.class);
        return true;

    }
}
