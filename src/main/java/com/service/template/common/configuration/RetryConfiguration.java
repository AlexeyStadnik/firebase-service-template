package com.service.template.common.configuration;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResourceAccessException;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@Configuration
@RequiredArgsConstructor
public class RetryConfiguration {

    @Bean
    public Retry requestRetry() {
        final IntervalFunction intervalFunction = IntervalFunction.ofExponentialBackoff(Duration.of(2, SECONDS));
        final var config = RetryConfig.custom()
                .maxAttempts(3)
                .retryOnResult(e -> e instanceof ResourceAccessException)
                .intervalFunction(intervalFunction)
                .build();
        return Retry.of("requestRetry", config);
    }
}
