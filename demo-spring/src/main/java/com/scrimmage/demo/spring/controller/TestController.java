package com.scrimmage.demo.spring.controller;

import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;
import com.scrimmage.common.service.IRewardService;
import com.scrimmage.common.service.IStatusService;
import com.scrimmage.common.service.IUserService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  private final IUserService userService;
  private final IStatusService statusService;
  private final IRewardService rewardService;


  public TestController(IUserService userService,
      IStatusService statusService,
      IRewardService rewardService) {
    this.userService = userService;
    this.statusService = statusService;
    this.rewardService = rewardService;
  }

  @GetMapping("/api/token")
  public TokenResponseDTO testToken() {
    TokenOption tokenOption = new TokenOption();
    tokenOption.setId("nanachi");
    return userService.getUserToken(tokenOption);
  }

  @GetMapping("/api/verify/services")
  public boolean verifyServices() {
   return this.statusService.verify();
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

    return this.rewardService.trackRewardable(rewardable,
        reward);
  }
}
