package com.example.demo.global.exception.customexception;

import com.example.demo.constant.ErrorInstruction;
import com.example.demo.global.exception.errorcode.UserErrorCode;

public class UserCustomException extends CustomExceptionWithInstruct {

    public static final UserCustomException ALREADY_EMAIL =
            new UserCustomException(UserErrorCode.IN_USE_EMAIL, ErrorInstruction.MESSAGE);

    public static final UserCustomException NOT_FOUND_USER =
            new UserCustomException(UserErrorCode.NOT_FOUND_USER, ErrorInstruction.REDIRECT);

    public static final UserCustomException ALREADY_NICKNAME =
            new UserCustomException(UserErrorCode.IN_USE_NICKNAME, ErrorInstruction.MESSAGE);

    public static final UserCustomException DOES_NOT_EXIST_PROFILE_IMG =
            new UserCustomException(UserErrorCode.NO_PROFILE_IMG, ErrorInstruction.MESSAGE);


    public UserCustomException(UserErrorCode userErrorCode, ErrorInstruction errorInstruction) {
        super(userErrorCode, errorInstruction);
    }
}
