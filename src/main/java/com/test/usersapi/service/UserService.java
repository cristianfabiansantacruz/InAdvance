package com.test.usersapi.service;

import com.test.usersapi.config.exceptions.CustomException;
import com.test.usersapi.controller.dto.UserLoginRequest;
import com.test.usersapi.controller.dto.UserLoginResponse;
import com.test.usersapi.controller.dto.UserRequest;
import com.test.usersapi.controller.dto.UserResponse;
import com.test.usersapi.domain.entity.PhoneEntity;
import com.test.usersapi.domain.entity.UserEntity;
import com.test.usersapi.mapper.PhoneMapper;
import com.test.usersapi.mapper.UserMapper;
import com.test.usersapi.domain.respository.IPhoneRepository;
import com.test.usersapi.domain.respository.IUserRepository;
import com.test.usersapi.util.Constants;
import com.test.usersapi.util.JwtTokenUtilService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final IUserRepository iUserRepository;
    private final IPhoneRepository iPhoneRepository;

    private final JwtTokenUtilService jwtTokenUtilService;

    public UserService(IUserRepository iUserRepository, IPhoneRepository iPhoneRepository, JwtTokenUtilService jwtTokenUtilService){
        this.iUserRepository = iUserRepository;
        this.iPhoneRepository = iPhoneRepository;
        this.jwtTokenUtilService = jwtTokenUtilService;
    }

    public UserResponse createUser(UserRequest request) throws CustomException{

        emailExist(request.email());

        String token = jwtTokenUtilService.getJWTToken(request.email());

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
            throw new CustomException(Constants.EMAIL_EXIST_ERROR_MSG, HttpStatus.BAD_REQUEST);
    }


    public UserLoginResponse login(UserLoginRequest request) throws CustomException {
        Optional<UserEntity> userLoginOptional = iUserRepository.findByEmailAndPassword(request.email(), request.password());
        if (userLoginOptional.isEmpty()){
            throw new CustomException(Constants.EMAIL_OR_PASSWORD_ARE_WRONG, HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = userLoginOptional.get();
        userEntity.setLastLoginDate(new Date());
        userEntity.setToken(jwtTokenUtilService.getJWTToken(userEntity.getEmail()));
        iUserRepository.saveAndFlush(userEntity);
        return new UserLoginResponse(userEntity.getToken());
    }
}
