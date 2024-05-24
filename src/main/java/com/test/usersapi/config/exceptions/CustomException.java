package com.test.usersapi.config.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception{

    private HttpStatus status;
    private String messageError;
    public CustomException(String msg, HttpStatus status){
        super(msg);
        this.messageError = msg;
        this.status = status;
    }

    public CustomException(String msg){
        super(msg);
        this.messageError = msg;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
