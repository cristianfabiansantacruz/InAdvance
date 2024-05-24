package com.test.usersapi.mapper;

import com.test.usersapi.controller.dto.UserRequest;
import com.test.usersapi.controller.dto.UserResponse;
import com.test.usersapi.domain.entity.UserEntity;
import com.test.usersapi.util.Constants;

public class UserMapper {

    public static UserResponse entityToDto(UserEntity userEntity) {

        return UserResponse.builder().id(userEntity.getId())
                .created(Constants.formatDate(userEntity.getCreateDate()))
                .token(userEntity.getToken())
                .lastLogin(Constants.formatDate(userEntity.getLastLoginDate()))
                .modified(Constants.formatDate(userEntity.getModifiedDate()))
                .isActive(userEntity.getIsActive())
                .build();
    }

    public static UserEntity dtoToEntity(UserRequest userRequest, String token) {

        return UserEntity.builder().name(userRequest.name())
                .email(userRequest.email())
                .password(userRequest.password())
                .token(token)
                .build();
    }
}
