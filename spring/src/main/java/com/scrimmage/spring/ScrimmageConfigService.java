package com.scrimmage.spring;

import com.scrimmage.common.constant.LogLevel;
import jakarta.annotation.PostConstruct;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

  @Value("${log.level:log}")
  private LogLevel logLevel;
  @Value("${api.server.endpoint.secure:false}")
  private Boolean apiServerEndpointSecure;
  @Value("${api.server.endpoint.validate:false}")
  private Boolean aiServerEndpointValidate;
  @Value("${api.server.endpoint}")
  private String apiServerEndpoint;
  @Value("${private.key}")
  private String privateKey;

  @PostConstruct
  public void init() {
    this.validateProtocol(apiServerEndpoint, apiServerEndpointSecure);

    if (namespace == null || namespace.isEmpty()) {
      throw new RuntimeException("namespace is required");
    }
    if (privateKey == null || privateKey.isEmpty()) {
      throw new RuntimeException("private.key is required");
    }
  }

  @Value("${namespace}")
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
