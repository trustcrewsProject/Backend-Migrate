package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.CommonErrorCode;

public class CommonCustomException extends CustomException {

    public static final CommonCustomException DOES_NOT_EXIST_PARAMETER_IN_METHOD =
            new CommonCustomException(CommonErrorCode.DOES_NOT_EXIST_PARAMETER_IN_METHOD);

    public CommonCustomException(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
