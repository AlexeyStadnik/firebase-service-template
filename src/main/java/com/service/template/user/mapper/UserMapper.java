package com.service.template.user.mapper;

import com.service.model.rest.RestUser;
import com.service.template.common.security.UserRole;
import com.service.template.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        imports = {
                UserRole.class
        }
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    UserEntity toEntity(RestUser model);

    RestUser toModel(UserEntity userEntity);

}
