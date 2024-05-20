package com.scrimmage.core;

import com.scrimmage.common.constant.LogLevel;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import lombok.Data;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

@Data
public class ScrimmageConfigService {

  public static ScrimmageConfigService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ScrimmageConfigService();
    }
    return INSTANCE;
  }
  private static  ScrimmageConfigService INSTANCE;

  private ScrimmageConfigService() {
    Yaml yaml = new Yaml();
    InputStream resourceAsStream = this.getClass().getClassLoader()
        .getResourceAsStream("application.yaml");
    Map<String, String> scrimmageConfigService = yaml.load(resourceAsStream);
    this.logLevel = LogLevel.getEnum(scrimmageConfigService.get("logLevel"));
    this.secure = Boolean.valueOf(Objects.toString(scrimmageConfigService.get("secure")));
    this.validateApiServerEndpoint =Boolean.valueOf(Objects.toString(scrimmageConfigService.get("validateApiServerEndpoint")));
    this.apiServerEndpoint = scrimmageConfigService.get("apiServerEndpoint");
    this.privateKey = scrimmageConfigService.get("privateKey");
    this.namespace = scrimmageConfigService.get("namespace");
  }

  private LogLevel logLevel;

  private Boolean secure;

  private Boolean validateApiServerEndpoint;

  private String apiServerEndpoint;

  private String privateKey;

  private String namespace;

}
