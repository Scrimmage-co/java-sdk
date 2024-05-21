package com.scrimmage.core;

import com.scrimmage.common.exceptions.AccountNotLinkedException;
import com.scrimmage.common.exceptions.ScrimmageServiceUnavailable;
import com.scrimmage.common.service.IAPIService;
import com.scrimmage.common.service.ILoggerService;
import com.scrimmage.common.service.IStatusService;

public class ScrimmageStatusService implements IStatusService {

  public static ScrimmageStatusService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ScrimmageStatusService(ScrimmageServiceFactory.api,
          ScrimmageServiceFactory.logger,
          ScrimmageServiceFactory.config);
    }
    return INSTANCE;
  }

  private static ScrimmageStatusService INSTANCE;

  private final IAPIService iApiService;
  private final ILoggerService loggerService;
  private final ScrimmageConfigService scrimmageConfigService;


  private ScrimmageStatusService(IAPIService iapiService, ILoggerService loggerService,
      ScrimmageConfigService scrimmageConfigService) {
    this.iApiService = iapiService;
    this.loggerService = loggerService;
    this.scrimmageConfigService = scrimmageConfigService;
  }

  @Override
  public boolean verify() {
    if (!scrimmageConfigService.getApiServerEndpointValidate()) {
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
