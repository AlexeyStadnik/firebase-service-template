package com.service.template.common.security.impl;

import com.service.template.common.configuration.AppConfiguration;
import com.service.template.common.security.ApiKey;
import com.service.template.common.security.TokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component("apiKeyValidator")
@RequiredArgsConstructor
public class ApiKeyTokenValidatorImpl implements TokenValidator {

    private final AppConfiguration appConfiguration;

    @Override
    public boolean validateAccessToken(final String apiKey) {
        final Set<ApiKey> apiKeys = appConfiguration.getSecurity().getApiKeys();
        return apiKeys != null && apiKeys.stream().anyMatch(key -> apiKey.equals(key.getKey()));
    }
}
