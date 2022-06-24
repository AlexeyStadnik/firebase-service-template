package com.service.template.common.security.impl;

import com.service.template.common.security.SecurityResolver;
import com.service.template.common.security.TokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("firebaseTokenValidator")
@RequiredArgsConstructor
public class FirebaseTokenValidatorImpl implements TokenValidator {

    private final SecurityResolver securityResolver;

    @Override
    public boolean validateAccessToken(final String accessToken) {
        securityResolver.verifyIdToken(accessToken);
        return true;
    }
}
