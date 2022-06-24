package com.service.template.common.configuration;

import com.service.template.common.security.AuthenticationProcessingFilter;
import com.service.template.common.security.JwtAuthenticationEntryPoint;
import com.service.template.common.security.TokenValidator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.http.HttpMethod.OPTIONS;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider tokenAuthenticationProvider;
    private final AuthenticationProvider apiKeyAuthenticationProvider;
    private final CorsFilter corsFilter;

    private final TokenValidator apiKeyValidator;
    private final TokenValidator firebaseTokenValidator;

    private final JwtAuthenticationEntryPoint entryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable();

        httpSecurity
                .cors()
                .and();

        httpSecurity
                .exceptionHandling().authenticationEntryPoint(entryPoint);

        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity
                .addFilterBefore(corsFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(new AuthenticationProcessingFilter(firebaseTokenValidator, apiKeyValidator),
                        BasicAuthenticationFilter.class);

        httpSecurity
                .authenticationProvider(apiKeyAuthenticationProvider)
                .authenticationProvider(tokenAuthenticationProvider);

        httpSecurity.authorizeRequests()
                .antMatchers(OPTIONS, "/**").permitAll()
                .antMatchers("/service-template/api/v1/**").authenticated();

        return httpSecurity.build();
    }
}
