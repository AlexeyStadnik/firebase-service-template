package com.service.template.common.security.provider;

import com.service.template.common.security.SecurityResolver;
import com.service.template.common.security.TokenValidator;
import com.service.template.common.security.UserDetailsImpl;
import com.service.template.common.security.UserRole;
import com.service.template.common.security.authentication.TokenAuthentication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final TokenValidator firebaseTokenValidator;
    private final SecurityResolver securityResolver;

    @Override
    public Authentication authenticate(final Authentication authentication) {

        final TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;
        final String accessToken = tokenAuthentication.getName();
        boolean isTokenValid;
        try {
            isTokenValid = firebaseTokenValidator.validateAccessToken(accessToken);
        } catch (Exception e) {
            isTokenValid = false;
        }
        if (isTokenValid) {
            final UserDetails userDetails
                    = new UserDetailsImpl(securityResolver.getUserIdByToken(accessToken), UserRole.USER);

            tokenAuthentication.setUserDetails(userDetails);
            tokenAuthentication.setAuthenticated(true);
        } else {
            tokenAuthentication.setAuthenticated(false);
        }

        return tokenAuthentication;
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
