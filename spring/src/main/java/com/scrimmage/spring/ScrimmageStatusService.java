package com.scrimmage.spring;

import com.scrimmage.common.exceptions.AccountNotLinkedException;
import com.scrimmage.common.exceptions.ScrimmageServiceUnavailable;
import com.scrimmage.common.service.IAPIService;
import com.scrimmage.common.service.ILoggerService;
import com.scrimmage.common.service.IStatusService;

public class ScrimmageStatusService implements IStatusService {

  private final IAPIService iApiService;
  private final ILoggerService loggerService;
  private final ScrimmageConfig scrimmageConfig;

  public ScrimmageStatusService(IAPIService iapiService, ILoggerService loggerService,
      ScrimmageConfig scrimmageConfig) {
    this.iApiService = iapiService;
    this.loggerService = loggerService;
    this.scrimmageConfig = scrimmageConfig;
  }

  @Override
  public boolean verify() {
    if (!scrimmageConfig.getApiServerEndpointValidate()) {
      loggerService.log("Skip scrimmage API server endpoint validation.");
      return true;
    }
    try {
      boolean serviceStatus = this.iApiService.getOverallServiceStatus();
      if (!serviceStatus) {
        loggerService.error("Rewarder API is not available");
        throw new ScrimmageServiceUnavailable("Rewarder API is not available");
      }
      boolean rewardKeyDetails = this.iApiService.getRewarderKeyDetails();
      if (!rewardKeyDetails) {
        loggerService.error("Rewarder API is not available");
        throw new ScrimmageServiceUnavailable("Rewarder API is not available");
      }
    } catch (Exception ex) {
      loggerService.error("Rewarder API key is invalid");
      throw new AccountNotLinkedException("Rewarder API is not available");
    }
    return true;
  }
}
