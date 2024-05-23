package com.scrimmage.core;

import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.service.IAPIService;
import com.scrimmage.common.service.IRewardService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrimmageRewardService implements IRewardService {

  private final IAPIService iApiService;

  public ScrimmageRewardService(IAPIService iapiService) {
    this.iApiService = iapiService;
  }


  @Override
  public List<RewardableEventDTO> trackRewardable(Rewardable rewardable, Reward... rewards) {
    List<RewardableEventDTO> events = new ArrayList<>();
    Arrays.asList(rewards).forEach(r -> {
      events.add(iApiService.createIntegrationReward(rewardable, null, r));
    });
    return events;
  }

  @Override
  public RewardableEventDTO trackRewardableOnce(Rewardable rewardable, String uniqueId,
      Reward reward) {
    return iApiService.createIntegrationReward(rewardable, uniqueId, reward);
  }
}
