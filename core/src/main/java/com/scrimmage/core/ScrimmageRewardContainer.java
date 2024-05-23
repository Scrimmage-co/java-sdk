package com.scrimmage.core;

import lombok.Getter;


@Getter
public class ScrimmageRewardContainer {

  private final ScrimmageConfig config;
  private final ScrimmageLoggerService logger;
  private final ScrimmageAPIService api;
  private final ScrimmageRewardService reward;
  private final ScrimmageStatusService status;
  private final ScrimmageUserService user;

  public ScrimmageRewardContainer(ScrimmageConfig scrimmageConfig) {
    this.config = scrimmageConfig;
    this.logger = new ScrimmageLoggerService(this.config);
    this.logger.log("Logger Initiated");
    this.api = new ScrimmageAPIService(this.config, this.logger);
    this.reward = new ScrimmageRewardService(this.api);
    this.status = new ScrimmageStatusService(this.api, this.logger, this.config);
    this.status.verify();
    this.user = new ScrimmageUserService(this.api);
  }

}
