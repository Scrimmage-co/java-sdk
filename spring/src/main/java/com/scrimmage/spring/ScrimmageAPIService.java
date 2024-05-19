package com.scrimmage.spring;

import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.TokenOptions;
import com.scrimmage.common.service.IAPIService;
import org.springframework.web.client.RestTemplate;

public class ScrimmageAPIService implements IAPIService {

  private ScrimmageConfigService scrimmageConfigService;

  public ScrimmageAPIService(ScrimmageConfigService scrimmageConfigService) {
    this.scrimmageConfigService = scrimmageConfigService;
  }

  @Override
  public RewardableEventDTO createIntegrationReward(Rewardable rewardable, String uniqueId,
      Reward reward) {
//    RestTemplate restTemplate = new RestTemplate();
//    restTemplate.postForEntity(scrimmageConfigService.apiServerEndpoint+"/integrations/rewards"
//        ,)
//       const eventId =
//        typeof eventIdOrReward === 'string' ? eventIdOrReward : undefined;
//    const rewardable =
//        typeof eventIdOrReward === 'string' ? reward : eventIdOrReward;
//    const privateKey = this.config.getPrivateKeyOrThrow();
//    const serviceUrl = this.config.getServiceUrl('api');
//    const namespace = this.config.getNamespaceOrThrow();
//    const httpClient = this.config.getHttpClientOrThrow();
//
//    try {
//      const response = await httpClient.post<IRewardableEventDTO>(
//        `${serviceUrl}/integrations/rewards`,
//      {
//        eventId,
//            userId,
//            dataType,
//            body: rewardable,
//      },
//      {
//        headers: {
//          Authorization: `Token ${privateKey}`,
//          'Scrimmage-Namespace': namespace,
//        },
//      },
//      );
//      return response.data;
//    } catch (error) {
//      if (error.response.status === HttpStatusCode.NotFound) {
//        return Promise.reject(new AccountNotLinkedException(reward.userId));
//      }
//      return Promise.reject(error);
//    }

    return null;
  }

  @Override
  public String getUserToken(String userId, TokenOptions options) {
    RestTemplate restTemplate = new RestTemplate();

    return "";
  }

  @Override
  public boolean getOverallServiceStatus() {
    RestTemplate restTemplate = new RestTemplate();

    return false;
  }

  @Override
  public void getRewarderKeyDetails() {
    RestTemplate restTemplate = new RestTemplate();
  }
}
