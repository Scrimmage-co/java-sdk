package com.scrimmage.demo.spring.controller;

import com.scrimmage.common.dto.Reward;
import com.scrimmage.common.dto.Rewardable;
import com.scrimmage.common.dto.RewardableEventDTO;
import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;
import com.scrimmage.demo.spring.container.Container;
import com.scrimmage.demo.spring.container.ContainerType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  private final Map<ContainerType, Container> containerTypeContainerMap = new HashMap<>();

  public TestController(List<Container> containers) {
    containers.forEach(container -> containerTypeContainerMap.put(container.getType(), container));
  }

  @GetMapping("/api/token")
  public TokenResponseDTO testToken() {
    TokenOption tokenOption = new TokenOption();
    tokenOption.setId("java_nanachi");
    return containerTypeContainerMap.get(ContainerType.TYPE1).getUser().getUserToken(tokenOption);
  }

  @GetMapping("/api/verify/services")
  public boolean verifyServices() {
    return containerTypeContainerMap.get(ContainerType.TYPE1).getStatus().verify();
  }

  @GetMapping("/api/reward")
  public List<RewardableEventDTO> testReward() {
    Rewardable rewardable = Rewardable.builder()
        .userId("java_nanachi")
        .type("java_helloWorld")
        .build();
    Reward reward = Reward.builder()
        .amount(1D)
        .currency("USD")
        .build();

    return containerTypeContainerMap.get(ContainerType.TYPE2).getReward()
        .trackRewardable(rewardable, reward);

  }
}
