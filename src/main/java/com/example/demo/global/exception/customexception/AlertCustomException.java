package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.AlertErrorCode;
import com.example.demo.global.exception.errorcode.BoardErrorCode;

public class AlertCustomException extends CustomException {

    public static final AlertCustomException NOT_FOUND_ALERT =
            new AlertCustomException(AlertErrorCode.NOT_FOUND_ALERT);

    public AlertCustomException(AlertErrorCode alertErrorCode) {
        super(alertErrorCode);
    }
}
