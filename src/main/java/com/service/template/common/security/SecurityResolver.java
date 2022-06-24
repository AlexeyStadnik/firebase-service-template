package com.service.template.common.security;

import com.google.firebase.auth.FirebaseToken;

public interface SecurityResolver {

    String getUserIdByToken(String token);

    FirebaseToken verifyIdToken(String accessToken);
}
