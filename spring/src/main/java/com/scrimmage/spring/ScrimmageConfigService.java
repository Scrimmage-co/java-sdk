package com.scrimmage.spring;

import com.scrimmage.common.constant.LogLevel;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ScrimmageConfigService {

  @PostConstruct
  public void init() {

  }

  @Value("${logLevel:log}")
  private LogLevel logLevel;

  @Value("${secure:true}")
  private Boolean secure;

  @Value("${validateApiServerEndpoint:true}")
  private Boolean validateApiServerEndpoint;

  @Value("${apiServerEndpoint}")
  private String apiServerEndpoint;

  @Value("${privateKey}")
  private String privateKey;

  @Value("${namespace}")
  private String namespace;

}
