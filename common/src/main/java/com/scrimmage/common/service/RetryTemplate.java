package com.scrimmage.common.service;

import com.scrimmage.common.exceptions.ScrimmageServiceUnavailable;
import com.scrimmage.common.helper.ExponentialTimeHelper;

import java.io.IOException;

public abstract class RetryTemplate<B, R> {

    private final ILoggerService logger;
    private int attempts = 0;
    private final int retryConfig;

    public RetryTemplate(int retryConfig, ILoggerService logger) {
        this.retryConfig = retryConfig;
        this.logger = logger;
    }

    public R exchangeWithRetry(String serviceUrl, String httpMethod, B requestBody, Class<R> responseType) {
        do {
            try {
                return this.exchange(serviceUrl, httpMethod, requestBody, responseType);
            } catch (Exception ex) {
                logger.error("Error while requesting", serviceUrl, ex);
            }
            if (attempts < retryConfig) {
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

    public abstract R exchange(String serviceUrl, String httpMethod, B requestBody,
                               Class<R> responseType) throws IOException, InterruptedException;
}
