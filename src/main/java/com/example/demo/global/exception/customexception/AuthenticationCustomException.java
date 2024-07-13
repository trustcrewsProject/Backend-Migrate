package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.UserErrorCode;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationCustomException extends AuthenticationException {

    public static final AuthenticationCustomException AUTHENTICATION_FAIL =
            new AuthenticationCustomException(UserErrorCode.AUTHENTICATION_FAIL.getMessage());

    public AuthenticationCustomException(String msg) {
        super(msg);
    }
}
