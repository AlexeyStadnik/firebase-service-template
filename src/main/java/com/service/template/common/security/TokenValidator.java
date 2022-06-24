package com.service.template.common.security;

public interface TokenValidator {

    boolean validateAccessToken(String accessToken);
}
