package com.scrimmage.spring;

import com.scrimmage.common.constant.LogLevel;
import com.scrimmage.common.dto.ScrimmageApiServiceType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrimmageConfigService {
  String logLevel = LogLevel.LOG.toString();
  boolean secure = true;
  boolean validateApiServerEndpoint = true;
  String apiServerEndpoint;
  Map<ScrimmageApiServiceType, String> privateKey;
  private String namespace;

}
