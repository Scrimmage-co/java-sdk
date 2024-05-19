package com.scrimmage.common.service;

import com.scrimmage.common.dto.TokenOptions;

public interface IUserService {

  String getUserToken(String userId, TokenOptions options);
}
