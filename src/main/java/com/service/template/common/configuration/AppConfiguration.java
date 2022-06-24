package com.service.template.common.configuration;

import com.service.template.common.security.ApiKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {

    private Aws aws = new Aws();
    private Security security = new Security();
    private Firebase firebase = new Firebase();
    private Sentry sentry = new Sentry();

    @Getter
    @Setter
    public static class Security {
        private Set<ApiKey> apiKeys = new HashSet<>();
    }

    @Getter
    @Setter
    public static class Firebase {

        private String serviceAccount;
    }

    @Getter
    @Setter
    public static class Aws {

        private String region;
        private String accessKey;
        private String secretKey;
    }

    @Getter
    @Setter
    public static class Sentry {

        private String dsn;
        private String environment;

    }

}
