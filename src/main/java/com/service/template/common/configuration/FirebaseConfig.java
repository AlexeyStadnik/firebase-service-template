package com.service.template.common.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.service.template.common.security.SecurityResolver;
import com.service.template.common.security.impl.FirebaseSecurityResolver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

@Profile("!test")
@Configuration
@RequiredArgsConstructor
public class FirebaseConfig {

    private final AppConfiguration appConfig;

    @SneakyThrows
    @PostConstruct
    public void initializeFirebaseApp() {
        final String serviceAccountBase64 = appConfig.getFirebase().getServiceAccount();
        if (ObjectUtils.isEmpty(serviceAccountBase64)) {
            throw new RuntimeException("Firebase service account is required to run a service");
        }
        final byte[] decodedBytes = Base64.getDecoder().decode(serviceAccountBase64);
        try (InputStream serviceAccount = new ByteArrayInputStream(decodedBytes)) {
            final FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }

    @Bean
    public FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Bean
    public SecurityResolver securityResolver(final FirebaseAuth firebaseAuth) {
        return new FirebaseSecurityResolver(firebaseAuth);
    }
}
