package com.example.demo.global.exception.customexception;

import com.example.demo.constant.ErrorInstruction;
import com.example.demo.global.exception.errorcode.ProjectErrorCode;

public class ProjectCustomException extends CustomExceptionWithInstruct {

    public static final ProjectCustomException NOT_FOUND_PROJECT =
            new ProjectCustomException(ProjectErrorCode.NOT_FOUND_PROJECT, ErrorInstruction.REDIRECT);

    public static final ProjectCustomException NO_PERMISSION_TO_TASK =
            new ProjectCustomException(ProjectErrorCode.NO_PERMISSION_TO_TASK, ErrorInstruction.MESSAGE);

    public static final ProjectCustomException ACCESS_NOT_ALLOWED =
            new ProjectCustomException(ProjectErrorCode.ACCESS_NOT_ALLOWED, ErrorInstruction.REDIRECT);

    public ProjectCustomException(ProjectErrorCode projectErrorCode, ErrorInstruction errorInstruction) {
        super(projectErrorCode, errorInstruction);
    }
}
