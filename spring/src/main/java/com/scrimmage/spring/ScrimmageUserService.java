package com.scrimmage.spring;

import com.scrimmage.common.dto.TokenOptions;
import com.scrimmage.common.service.IAPIService;
import com.scrimmage.common.service.IUserService;

public class ScrimmageUserService implements IUserService {

  private final IAPIService iApiService;

  public ScrimmageUserService(IAPIService iapiService) {
    this.iApiService = iapiService;
  }

  @Override
  public String getUserToken(String userId, TokenOptions options) {
    return iApiService.getUserToken(userId, options);
  }
}
