package com.scrimmage.core;

public class ScrimmageServiceFactory {

  public static final ScrimmageLoggerService logger = ScrimmageLoggerService.getInstance();
  public static final ScrimmageConfigService config = ScrimmageConfigService.getInstance();
  public static final ScrimmageAPIService api = ScrimmageAPIService.getInstance();
  public static final ScrimmageRewardService reward = ScrimmageRewardService.getInstance();
  public static final ScrimmageStatusService status = ScrimmageStatusService.getInstance();
  public static final ScrimmageUserService user = ScrimmageUserService.getInstance();
}
