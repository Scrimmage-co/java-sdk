package com.scrimmage.demo.spring.container;

import com.scrimmage.common.constant.LogLevel;
import com.scrimmage.spring.ScrimmageConfig;
import org.springframework.stereotype.Component;


@Component
public class ScrimmageType2RewardContainer extends Container {


  public ScrimmageType2RewardContainer() {
    super(getScrimmage());
  }

  private static ScrimmageConfig getScrimmage() {
    return ScrimmageConfig
        .builder()
        .namespace("")
        .privateKey("")
        .apiServerEndpoint("")
        .logLevel(LogLevel.LOG)
        .apiServerEndpointSecure(false)
        .apiServerEndpointValidate(false)
            .retry(0)
        .build();
  }


  public ContainerType getType() {
    return ContainerType.TYPE2;
  }
}
