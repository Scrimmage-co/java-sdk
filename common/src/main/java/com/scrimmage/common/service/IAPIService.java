package com.scrimmage.common.service;

import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.TokenOptions;

public interface IAPIService {

  RewardableEventDTO createIntegrationReward(Rewardable rewardable, String uniqueId,
      Reward reward);

  String getUserToken(String userId, TokenOptions options);

  boolean getOverallServiceStatus();

  void getRewarderKeyDetails();

}
