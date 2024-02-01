package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.UserProjectHistoryErrorCode;

public class UserProjectHistoryCustomException extends CustomException{

    public static final UserProjectHistoryCustomException NOT_FOUND_USER_PROJECT_HISTORY =
            new UserProjectHistoryCustomException(UserProjectHistoryErrorCode.NOT_FOUND_USER_PROJECT_HISTORY);

    public UserProjectHistoryCustomException(UserProjectHistoryErrorCode errorCode) {
        super(errorCode);
    }
}
