package com.scrimmage.spring;

import com.scrimmage.common.service.ILoggerService;
import com.scrimmage.common.service.RetryTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateWithRetry<B, R> extends RetryTemplate<B, R> {

    private final ScrimmageConfig scrimmageConfig;

    public RestTemplateWithRetry(ScrimmageConfig scrimmageConfig,
                                 ILoggerService logger) {
        super(scrimmageConfig.getRetry(), logger);
        this.scrimmageConfig = scrimmageConfig;
    }

    @Override
    public R exchange(String serviceUrl, String httpMethod, B requestBody, Class<R> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        String privateKey = this.scrimmageConfig.getPrivateKey();
        String namespace = this.scrimmageConfig.getNamespace();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", privateKey);
        headers.set("Scrimmage-Namespace", namespace);
        HttpEntity entity = requestBody == null ? new HttpEntity<>(headers)
                : new HttpEntity<>(requestBody, headers);

        ResponseEntity<R> exchange = restTemplate.exchange(
                serviceUrl,
                HttpMethod.valueOf(httpMethod),
                entity,
                responseType);
        return exchange.getBody();

    }
}

