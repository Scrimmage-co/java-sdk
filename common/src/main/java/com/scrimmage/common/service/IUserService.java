package com.scrimmage.common.service;

import com.scrimmage.common.dto.TokenOption;
import com.scrimmage.common.dto.TokenResponseDTO;

public interface IUserService {

  TokenResponseDTO getUserToken(TokenOption options);
}
