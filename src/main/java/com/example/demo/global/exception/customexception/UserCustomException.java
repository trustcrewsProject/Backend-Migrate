package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.UserErrorCode;

public class UserCustomException extends CustomException {

    public static final UserCustomException ALREADY_EMAIL =
            new UserCustomException(UserErrorCode.ALREADY_EMAIL);

    public static final UserCustomException NOT_FOUND_USER =
            new UserCustomException(UserErrorCode.NOT_FOUND_USER);

    public static final UserCustomException ALREADY_NICKNAME =
            new UserCustomException(UserErrorCode.ALREADY_NICKNAME);

    public static final UserCustomException DOES_NOT_EXIST_PROFILE_IMG =
            new UserCustomException(UserErrorCode.DOES_NOT_EXIST_PROFILE_IMG);

    public static final UserCustomException INVALID_AUTHENTICATION =
            new UserCustomException(UserErrorCode.INVALID_AUTHENTICATION);

    public static final UserCustomException ALREADY_OAUTH_USER =
            new UserCustomException(UserErrorCode.ALREADY_OAUTH_USER);

    public UserCustomException(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
