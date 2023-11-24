package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.UserErrorCode;

public class UserCustomException extends CustomException{

    public static final UserCustomException ALREADY_EMAIL = new UserCustomException(UserErrorCode.ALREADY_EMAIL);

    public UserCustomException(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
