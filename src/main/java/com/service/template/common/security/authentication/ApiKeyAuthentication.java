package com.service.template.common.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class ApiKeyAuthentication implements Authentication {

    private final String apiKey;
    private UserDetails userDetails;
    private boolean authenticated;

    public ApiKeyAuthentication(final String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(final boolean isAuthenticated) {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return apiKey;
    }

    public void setUserDetails(final UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
