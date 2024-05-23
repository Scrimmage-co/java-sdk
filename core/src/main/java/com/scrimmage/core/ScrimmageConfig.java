package com.scrimmage.core;

import com.scrimmage.common.constant.LogLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScrimmageConfig {

  @Default
  private Boolean apiServerEndpointSecure = true;

  @Default
  private LogLevel logLevel = LogLevel.INFO;

  @Default
  private Boolean apiServerEndpointValidate = true;
  private String apiServerEndpoint;
  private String privateKey;
  private String namespace;

  @Default
  private int retry = 0;

}