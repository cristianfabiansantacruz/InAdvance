package com.test.usersapi.controller;

import com.test.usersapi.config.exceptions.CustomException;
import com.test.usersapi.controller.dto.UserLoginRequestDto;
import com.test.usersapi.controller.dto.UserLoginResponseDto;
import com.test.usersapi.controller.dto.UserRequestDto;
import com.test.usersapi.controller.dto.UserResponseDto;
import com.test.usersapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserRequestDto request) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto request) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request));
    }
}
