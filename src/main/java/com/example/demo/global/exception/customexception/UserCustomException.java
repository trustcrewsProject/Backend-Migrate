package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.UserErrorCode;

public class UserCustomException extends CustomException {

    public static final UserCustomException ALREADY_EMAIL =
            new UserCustomException(UserErrorCode.IN_USE_EMAIL);

    public static final UserCustomException NOT_FOUND_USER =
            new UserCustomException(UserErrorCode.NOT_FOUND_USER);

    public static final UserCustomException ALREADY_NICKNAME =
            new UserCustomException(UserErrorCode.IN_USE_NICKNAME);

    public static final UserCustomException DOES_NOT_EXIST_PROFILE_IMG =
            new UserCustomException(UserErrorCode.NO_PROFILE_IMG);

    public static final UserCustomException ALREADY_OAUTH_USER =
            new UserCustomException(UserErrorCode.IN_USE_OAUTH_USER);

    public UserCustomException(UserErrorCode userErrorCode) {
        super(userErrorCode);
    }
}
