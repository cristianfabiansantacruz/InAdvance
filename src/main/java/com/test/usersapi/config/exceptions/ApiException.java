package com.test.usersapi.config.exceptions;

import lombok.Data;
@Data
public class ApiException {
    private String message;

    public ApiException(String message ) {
        super();
        this.message = message;
    }
}
