package com.scrimmage.core;

import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;
import com.scrimmage.common.service.IAPIService;
import com.scrimmage.common.service.IUserService;

public class ScrimmageUserService implements IUserService {

  public final IAPIService iApiService;

  public ScrimmageUserService(IAPIService iapiService) {
    this.iApiService = iapiService;
  }

  @Override
  public TokenResponseDTO getUserToken(TokenOption options) {
    return iApiService.getUserToken(options);
  }
}
