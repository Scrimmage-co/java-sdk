package com.scrimmage.demo.core.controller;

import com.scrimmage.common.constant.LogLevel;
import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;
import com.scrimmage.core.ScrimmageConfig;
import com.scrimmage.core.ScrimmageRewardContainer;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  ScrimmageConfig scrimmageConfig = ScrimmageConfig
      .builder()
      .namespace("")
      .privateKey("")
      .apiServerEndpoint("")
      .apiServerEndpointSecure(true)
      .apiServerEndpointValidate(true)
      .retry(0)
      .build();

  @GetMapping("/api/token")
  public TokenResponseDTO testToken() {
    TokenOption tokenOption = new TokenOption();
    tokenOption.setId("");
    return new ScrimmageRewardContainer(scrimmageConfig).getApi().getUserToken(tokenOption);
  }

  @GetMapping("/api/reward")
  public List<RewardableEventDTO> testReward() {
    Rewardable rewardable = Rewardable.builder()
        .userId("")
        .type("")
        .build();
    Reward reward = Reward.builder()
        .amount(1D)
        .currency("USD")
        .build();
    ScrimmageConfig scrimmageConfig = ScrimmageConfig
        .builder()
        .namespace("")
        .privateKey(
            "")
        .apiServerEndpoint("")
        .logLevel(LogLevel.LOG)
        .apiServerEndpointSecure(true)
        .apiServerEndpointValidate(true)
        .retry(0)
        .build();

    return new ScrimmageRewardContainer(scrimmageConfig).getReward()
        .trackRewardable(rewardable,
        reward);
  }

  @GetMapping("/api/verify/services")
  public boolean verifyServices() {
    return new ScrimmageRewardContainer(scrimmageConfig).getStatus().verify();
  }
}
