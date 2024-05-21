package com.scrimmage.core;

import com.scrimmage.common.constant.LogLevel;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;

@Data
public class ScrimmageConfigService {

  public static ScrimmageConfigService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ScrimmageConfigService();
    }
    return INSTANCE;
  }
  private static  ScrimmageConfigService INSTANCE;

  private Boolean apiServerEndpointSecure;

  private LogLevel logLevel;
  private Boolean apiServerEndpointValidate;

  private ScrimmageConfigService() {
    Properties properties = new Properties();

    try (InputStream resourceAsStream = this.getClass().getClassLoader()
        .getResourceAsStream("application.properties")) {
      properties.load(resourceAsStream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.apiServerEndpointSecure =
        properties.get("api.server.endpoint.secure") != null && Boolean.parseBoolean(
            Objects.toString(properties.get("api.server.endpoint.secure")));

    this.apiServerEndpoint = String.valueOf(properties.get("api.server.endpoint"));

    validateProtocol(apiServerEndpoint, apiServerEndpointSecure);

    this.apiServerEndpointValidate =
        properties.get("api.server.endpoint.validate") != null && Boolean.parseBoolean(
            Objects.toString(properties.get("api.server.endpoint.validate")));

    this.logLevel = properties.get("log.level") != null ?
        LogLevel.getEnum(String.valueOf(properties.get("log.level")))
        : LogLevel.LOG;

    this.privateKey = String.valueOf(properties.get("private.key"));

    this.namespace = String.valueOf(properties.get("namespace"));

    if (namespace == null || namespace.isEmpty()) {
      throw new RuntimeException("namespace is required");
    }
    if (privateKey == null || privateKey.isEmpty()) {
      throw new RuntimeException("private.key is required");
    }
  }

  private String apiServerEndpoint;

  private String privateKey;

  private String namespace;

  void validateProtocol(String url, Boolean secure) {
    try {
      new URL(url).toURI();
      if (secure) {
        String regex = "^https://";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (!matcher.find()) {
          throw new RuntimeException(String.format("Invalid URL: %s", url));
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
