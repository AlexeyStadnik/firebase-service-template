package com.service.template.common.security.provider;

import com.service.template.common.configuration.AppConfiguration;
import com.service.template.common.security.ApiKey;
import com.service.template.common.security.TokenValidator;
import com.service.template.common.security.UserDetailsImpl;
import com.service.template.common.security.UserRole;
import com.service.template.common.security.authentication.ApiKeyAuthentication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final TokenValidator apiKeyValidator;
    private final AppConfiguration appConfig;

    @Override
    public Authentication authenticate(final Authentication authentication) {

        final ApiKeyAuthentication apiKeyAuthentication = (ApiKeyAuthentication) authentication;
        final String accessToken = apiKeyAuthentication.getName();

        if (apiKeyValidator.validateAccessToken(accessToken)) {
            final Optional<UserRole> roleOpt = getRole(accessToken);

            if (roleOpt.isPresent()) {
                final UserRole userRole = roleOpt.get();
                final UserDetails userDetails = new UserDetailsImpl(userRole.toString(), userRole);
                apiKeyAuthentication.setUserDetails(userDetails);
                apiKeyAuthentication.setAuthenticated(true);
            } else {
                apiKeyAuthentication.setAuthenticated(false);
            }
        } else {
            apiKeyAuthentication.setAuthenticated(false);
        }

        return apiKeyAuthentication;
    }

    private Optional<UserRole> getRole(final String accessToken) {
        final Optional<ApiKey> apiKey = appConfig.getSecurity().getApiKeys()
                .stream().filter(key -> key.getKey().equals(accessToken)).findFirst();

        return apiKey.map(key -> UserRole.fromValue(key.getRole()));
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return ApiKeyAuthentication.class.equals(authentication);
    }
}
