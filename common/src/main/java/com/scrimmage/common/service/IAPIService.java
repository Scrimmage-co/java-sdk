package com.scrimmage.common.service;

import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;

public interface IAPIService {

  RewardableEventDTO createIntegrationReward(Rewardable rewardable, String uniqueId,
      Reward reward);

  TokenResponseDTO getUserToken(TokenOption options);

  boolean getOverallServiceStatus();

  boolean getRewarderKeyDetails();

}
