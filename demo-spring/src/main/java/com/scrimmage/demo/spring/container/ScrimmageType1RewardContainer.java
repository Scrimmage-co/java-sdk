package com.scrimmage.demo.spring.container;

import com.scrimmage.spring.ScrimmageConfig;
import org.springframework.stereotype.Component;

@Component
public class ScrimmageType1RewardContainer extends Container {


  public ScrimmageType1RewardContainer() {
    super(getScrimmage());
  }

  private static ScrimmageConfig getScrimmage() {
    return ScrimmageConfig
        .builder()
        .namespace("")
        .privateKey("")
        .apiServerEndpoint("")
        .build();
  }


  public ContainerType getType() {
    return ContainerType.TYPE1;
  }


}
