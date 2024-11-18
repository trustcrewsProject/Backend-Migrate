package com.example.demo.global.exception.customexception;

import com.example.demo.constant.ErrorInstruction;
import com.example.demo.global.exception.errorcode.WorkErrorCode;

public class WorkCustomException extends CustomExceptionWithInstruct {

    public static final WorkCustomException NOT_FOUND_WORK =
            new WorkCustomException(WorkErrorCode.NOT_FOUND_WORK, ErrorInstruction.NONE);

    public static final WorkCustomException NO_PERMISSION_TO_TASK =
            new WorkCustomException(WorkErrorCode.NO_PERMISSION_TO_TASK, ErrorInstruction.NONE);

    public static final WorkCustomException CREATE_EXCEEDED_WORK =
            new WorkCustomException(WorkErrorCode.CREATE_EXCEEDED_WORK, ErrorInstruction.MESSAGE);

    public WorkCustomException(WorkErrorCode workErrorCode, ErrorInstruction errorInstruction) {
        super(workErrorCode, errorInstruction);
    }
}
