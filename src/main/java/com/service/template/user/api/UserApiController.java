package com.service.template.user.api;

import com.service.controllers.api.UserApi;
import com.service.model.rest.RestUser;
import com.service.template.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserApiController implements UserApi {

    private final UserService userService;

    @Override
    @PreAuthorize("hasAuthority('FIREBASE')")
    public ResponseEntity<RestUser> createUser(final RestUser restUser) {
        final RestUser created = userService.createUser(restUser);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    @PreAuthorize("hasAuthority('FIREBASE')")
    public ResponseEntity<Void> deleteUser(final String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PreAuthorize("(hasAuthority('ADMIN')) || (hasAuthority('USER') && @securityHelper.matchCurrentUserId(#userId))")
    public ResponseEntity<RestUser> retrieveUser(final String userId) {
        final RestUser retrieved = userService.retrieveUser(userId);
        return new ResponseEntity<>(retrieved, HttpStatus.OK);
    }
}
