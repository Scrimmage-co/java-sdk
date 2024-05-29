package com.scrimmage.spring;

import com.scrimmage.common.exceptions.ScrimmageServiceUnavailable;
import com.scrimmage.common.helper.ExponentialTimeHelper;
import com.scrimmage.common.service.ILoggerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateWithRetry<B, R> {

  private ScrimmageConfig scrimmageConfig;
  private ILoggerService logger;

  public RestTemplateWithRetry(ScrimmageConfig scrimmageConfig,
      ILoggerService logger) {
    this.scrimmageConfig = scrimmageConfig;
    this.logger = logger;
  }

  public R exchange(String serviceUrl, HttpMethod httpMethod, B requestBody,
      Class<R> responseType) {
    int attempts = 0;
    do {
      try {
        RestTemplate restTemplate = new RestTemplate();
        String privateKey = this.scrimmageConfig.getPrivateKey();
        String namespace = this.scrimmageConfig.getNamespace();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", privateKey);
        headers.set("Scrimmage-Namespace", namespace);

        ResponseEntity<R> response = restTemplate.exchange(
            serviceUrl,
            httpMethod,
            requestBody == null ? new HttpEntity<>(headers)
                : new HttpEntity<>(requestBody, headers),
            responseType);
        return response.getBody();
      } catch (Exception ex) {
        logger.error("Error while requesting", serviceUrl, ex);
      }
      if (attempts < scrimmageConfig.getRetry()) {
        attempts++;
        try {
          Thread.sleep(ExponentialTimeHelper.getExponentialTime(attempts));
          logger.info("Retrying... Attempt ", attempts);

        } catch (Exception ex) {
          logger.error("Error sleeping", ex);
        }
      } else {
        break;
      }
    } while (true);
    throw new ScrimmageServiceUnavailable(
        String.format("%s is not available", serviceUrl));
  }
}

