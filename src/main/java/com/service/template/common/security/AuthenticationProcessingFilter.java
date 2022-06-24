package com.service.template.common.security;

import com.service.template.common.security.authentication.ApiKeyAuthentication;
import com.service.template.common.security.authentication.TokenAuthentication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@AllArgsConstructor
public class AuthenticationProcessingFilter implements Filter {

    private static final String BEARER = "Bearer";
    private static final String API_KEY_HEADER = "X-API-KEY";

    private final TokenValidator firebaseTokenValidator;
    private final TokenValidator apiKeyValidator;

    @Override
    public void init(final FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain
    ) throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String token = httpServletRequest.getHeader(AUTHORIZATION);
        final String apiKey = httpServletRequest.getHeader(API_KEY_HEADER);

        if (token != null) {
            validateFirebaseIdToken(token);
        } else if (apiKey != null) {
            validateApiKeys(apiKey);
        }

        chain.doFilter(request, response);
    }

    private void validateApiKeys(final String apiKey) {
        final ApiKeyAuthentication apiKeyAuthentication = new ApiKeyAuthentication(apiKey);
        if (apiKeyValidator.validateAccessToken(apiKey)) {
            SecurityContextHolder.getContext().setAuthentication(apiKeyAuthentication);
        }
    }

    private void validateFirebaseIdToken(final String token) {
        try {
            final String tokenAuthorization = token.startsWith(BEARER) ? token.split(" ")[1] : token;
            final TokenAuthentication tokenAuthentication = new TokenAuthentication(tokenAuthorization);
            if (firebaseTokenValidator.validateAccessToken(tokenAuthorization)) {
                SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
            }
        } catch (Exception e) {
            log.debug("Error when validating id token", e);
        }
    }

    @Override
    public void destroy() {

    }
}
