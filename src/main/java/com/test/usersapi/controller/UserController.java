package com.test.usersapi.controller;

import com.test.usersapi.config.exceptions.CustomException;
import com.test.usersapi.controller.dto.UserLoginRequest;
import com.test.usersapi.controller.dto.UserLoginResponse;
import com.test.usersapi.controller.dto.UserRequest;
import com.test.usersapi.controller.dto.UserResponse;
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
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) throws CustomException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request) throws CustomException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request));
    }
}
