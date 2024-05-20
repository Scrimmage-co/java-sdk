package com.scrimmage.demo.core.controller;

import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;
import com.scrimmage.core.ScrimmageServiceFactory;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/api/token")
  public TokenResponseDTO testToken() {
    TokenOption tokenOption = new TokenOption();
    tokenOption.setId("nanachi");
    return ScrimmageServiceFactory.user.getUserToken(tokenOption);
  }

  @GetMapping("/api/reward")
  public List<RewardableEventDTO> testReward() {
    Rewardable rewardable = Rewardable.builder()
        .userId("nanachi")
        .type("helloWorld")
        .build();
    Reward reward = Reward.builder()
        .amount(1D)
        .currency("USD")
        .build();

    return ScrimmageServiceFactory.reward.trackRewardable(rewardable,
        reward);
  }

  @GetMapping("/api/verify/services")
  public boolean verifyServices() {
    return ScrimmageServiceFactory.status.verify();
  }
}
