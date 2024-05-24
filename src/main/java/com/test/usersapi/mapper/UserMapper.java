package com.test.usersapi.mapper;

import com.test.usersapi.controller.dto.UserRequestDto;
import com.test.usersapi.controller.dto.UserResponseDto;
import com.test.usersapi.domain.entity.UserEntity;
import com.test.usersapi.util.ConstantsUtil;

public class UserMapper {

    public static UserResponseDto entityToDto(UserEntity userEntity) {

        return UserResponseDto.builder().id(userEntity.getId())
                .created(ConstantsUtil.formatDate(userEntity.getCreateDate()))
                .token(userEntity.getToken())
                .lastLogin(ConstantsUtil.formatDate(userEntity.getLastLoginDate()))
                .modified(ConstantsUtil.formatDate(userEntity.getModifiedDate()))
                .isActive(userEntity.getIsActive())
                .build();
    }

    public static UserEntity dtoToEntity(UserRequestDto userRequest, String token) {

        return UserEntity.builder().name(userRequest.name())
                .email(userRequest.email())
                .password(userRequest.password())
                .token(token)
                .build();
    }
}
