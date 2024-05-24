package com.test.usersapi.service;

import com.test.usersapi.config.exceptions.CustomException;
import com.test.usersapi.controller.dto.UserLoginRequestDto;
import com.test.usersapi.controller.dto.UserLoginResponseDto;
import com.test.usersapi.controller.dto.UserRequestDto;
import com.test.usersapi.controller.dto.UserResponseDto;
import com.test.usersapi.domain.entity.PhoneEntity;
import com.test.usersapi.domain.entity.UserEntity;
import com.test.usersapi.mapper.PhoneMapper;
import com.test.usersapi.mapper.UserMapper;
import com.test.usersapi.domain.respository.IPhoneRepository;
import com.test.usersapi.domain.respository.IUserRepository;
import com.test.usersapi.util.ConstantsUtil;
import com.test.usersapi.util.JwtTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository iUserRepository;
    private final IPhoneRepository iPhoneRepository;

    private final JwtTokenService jwtTokenService;

    public UserService(IUserRepository iUserRepository, IPhoneRepository iPhoneRepository, JwtTokenService jwtTokenService){
        this.iUserRepository = iUserRepository;
        this.iPhoneRepository = iPhoneRepository;
        this.jwtTokenService = jwtTokenService;
    }

    public UserResponseDto createUser(UserRequestDto request) throws CustomException{

        emailExist(request.email());

        String token = jwtTokenService.getJWTToken(request.email());

        UserEntity newUserEntity = UserMapper.dtoToEntity(request, token);
        List<PhoneEntity> phoneEntityList = PhoneMapper.dtoToEntity(request.phones(), newUserEntity);
        newUserEntity.setPhones(phoneEntityList);
        newUserEntity = iUserRepository.saveAndFlush(newUserEntity);

//        List<PhoneEntity> phoneEntityList = PhoneMapper.dtoToEntity(request.getPhones(), newUserEntity);
        savePhonesUser(phoneEntityList);


        return UserMapper.entityToDto(newUserEntity);
    }

    private void savePhonesUser(List<PhoneEntity> phones){
        iPhoneRepository.saveAllAndFlush(phones);
    }

    private void emailExist(String email) throws CustomException {
        if(iUserRepository.findByEmail(email).isPresent())
            throw new CustomException(ConstantsUtil.EMAIL_EXIST_ERROR_MSG, HttpStatus.BAD_REQUEST);
    }


    public UserLoginResponseDto login(UserLoginRequestDto request) throws CustomException {
        Optional<UserEntity> userLoginOptional = iUserRepository.findByEmailAndPassword(request.email(), request.password());
        if (userLoginOptional.isEmpty()){
            throw new CustomException(ConstantsUtil.EMAIL_OR_PASSWORD_ARE_WRONG, HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = userLoginOptional.get();
        userEntity.setLastLoginDate(new Date());
        userEntity.setToken(jwtTokenService.getJWTToken(userEntity.getEmail()));
        iUserRepository.saveAndFlush(userEntity);
        return new UserLoginResponseDto(userEntity.getToken());
    }
}
