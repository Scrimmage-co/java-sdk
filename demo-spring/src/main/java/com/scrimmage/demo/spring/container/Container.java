package com.scrimmage.demo.spring.container;

import com.scrimmage.spring.ScrimmageAPIService;
import com.scrimmage.spring.ScrimmageConfig;
import com.scrimmage.spring.ScrimmageLoggerService;
import com.scrimmage.spring.ScrimmageRewardService;
import com.scrimmage.spring.ScrimmageStatusService;
import com.scrimmage.spring.ScrimmageUserService;
import lombok.Getter;

@Getter
public abstract class Container {

  private final ScrimmageConfig config;
  private final ScrimmageLoggerService logger;
  private final ScrimmageAPIService api;
  private final ScrimmageRewardService reward;
  private final ScrimmageStatusService status;
  private final ScrimmageUserService user;

  public Container(ScrimmageConfig scrimmageConfig) {
    this.config = scrimmageConfig;
    this.logger = new ScrimmageLoggerService(this.config);
    this.logger.info("Logger Initiated");
    this.api = new ScrimmageAPIService(this.config, this.logger);
    this.reward = new ScrimmageRewardService(this.api);
    this.status = new ScrimmageStatusService(this.api, this.logger, this.config);
    this.status.verify();
    this.user = new ScrimmageUserService(this.api);
  }

  public abstract ContainerType getType();
}
