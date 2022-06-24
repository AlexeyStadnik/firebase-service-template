package com.service.template.common.config;

import com.service.template.common.security.SecurityResolver;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfig {

    @Bean
    public SecurityResolver securityResolver() {
        return Mockito.mock(SecurityResolver.class);
    }
}
