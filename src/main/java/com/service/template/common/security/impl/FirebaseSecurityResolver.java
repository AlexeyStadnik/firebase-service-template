package com.service.template.common.security.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.service.template.common.security.SecurityResolver;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class FirebaseSecurityResolver implements SecurityResolver {

    private final FirebaseAuth firebaseAuth;

    @SneakyThrows
    @Override
    public String getUserIdByToken(final String token) {
        return firebaseAuth.verifyIdToken(token).getUid();
    }

    @SneakyThrows
    @Override
    public FirebaseToken verifyIdToken(final String accessToken) {
        return firebaseAuth.verifyIdToken(accessToken);
    }
}
