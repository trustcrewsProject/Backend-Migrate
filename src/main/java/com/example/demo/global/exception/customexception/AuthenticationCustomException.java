package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.UserErrorCode;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationCustomException extends AuthenticationException {

    public static final AuthenticationCustomException INVALID_AUTHENTICATION =
            new AuthenticationCustomException(UserErrorCode.INVALID_AUTHENTICATION.getMessage());

    public AuthenticationCustomException(String msg) {
        super(msg);
    }
}
