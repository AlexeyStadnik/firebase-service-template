package com.service.template.user.service.impl;

import com.service.model.rest.RestUser;
import com.service.template.common.exception.ServiceException;
import com.service.template.common.security.UserRole;
import com.service.template.user.entity.UserEntity;
import com.service.template.user.mapper.UserMapper;
import com.service.template.user.repository.UserRepository;
import com.service.template.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public RestUser createUser(final RestUser restUser) {
        final UserEntity userEntity = userMapper.toEntity(restUser);

        userEntity.setRole(UserRole.USER);

        if (userRepository.existsById(restUser.getId())) {
            throw new ServiceException(HttpStatus.UNPROCESSABLE_ENTITY, "User with this id already exists");
        }
        final UserEntity created = userRepository.save(userEntity);
        return userMapper.toModel(created);
    }

    @Override
    @Transactional
    public void deleteUser(final String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ServiceException(HttpStatus.NOT_FOUND, "User could not be found");
        }
        userRepository.softDeleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public RestUser retrieveUser(final String userId) {
        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "User could not be found"));

        return userMapper.toModel(userEntity);
    }
}
