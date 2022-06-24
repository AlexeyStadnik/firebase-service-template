package com.service.template.user.service;

import com.service.model.rest.RestUser;

public interface UserService {

    RestUser createUser(RestUser restUser);

    void deleteUser(String userId);

    RestUser retrieveUser(String userId);
}
