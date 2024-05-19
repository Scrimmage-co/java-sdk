package com.scrimmage.common.service;

import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import java.util.List;

public interface IRewardService {

  List<RewardableEventDTO> trackRewardable(Rewardable rewardable, Reward... rewards);

  RewardableEventDTO trackRewardableOnce(Rewardable rewardable, String uniqueId,
      Reward reward);
}
