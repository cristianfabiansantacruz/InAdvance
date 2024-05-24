package com.test.usersapi.mapper;

import com.test.usersapi.controller.dto.PhoneRequest;
import com.test.usersapi.domain.entity.PhoneEntity;
import com.test.usersapi.domain.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneMapper {

    public static List<PhoneEntity> dtoToEntity(List<PhoneRequest> phonesRequestList, UserEntity userEntity) {

        List<PhoneEntity> phoneEntityList = new ArrayList<>();
        if (phonesRequestList.isEmpty()) return phoneEntityList;

        return phonesRequestList.stream().map(e ->
                PhoneEntity.builder().user(userEntity).number(e.number()).cityCode(e.cityCode()).contryCode(e.countryCode()).build()
        ).collect(Collectors.toList());
    }

}
