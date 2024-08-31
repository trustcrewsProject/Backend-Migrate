package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.WorkErrorCode;

public class WorkCustomException extends CustomException {

    public static final WorkCustomException NOT_FOUND_WORK =
            new WorkCustomException(WorkErrorCode.NOT_FOUND_WORK);

    public static final WorkCustomException NO_PERMISSION_TO_TASK =
            new WorkCustomException(WorkErrorCode.NO_PERMISSION_TO_TASK);

    public static final WorkCustomException CREATE_EXCEEDED_WORK =
            new WorkCustomException(WorkErrorCode.CREATE_EXCEEDED_WORK);

    public WorkCustomException(WorkErrorCode workErrorCode) {
        super(workErrorCode);
    }
}
