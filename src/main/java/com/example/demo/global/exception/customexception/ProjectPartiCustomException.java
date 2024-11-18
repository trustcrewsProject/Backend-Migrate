package com.example.demo.global.exception.customexception;

import com.example.demo.constant.ErrorInstruction;
import com.example.demo.global.exception.errorcode.ProjectPartiErrorCode;

public class ProjectPartiCustomException extends CustomExceptionWithInstruct {

    public static final ProjectPartiCustomException PARTICIPATE_NOT_ALLOWED =
            new ProjectPartiCustomException(ProjectPartiErrorCode.PARTICIPATE_NOT_ALLOWED, ErrorInstruction.MESSAGE);

    public static final ProjectPartiCustomException PARTICIPATE_DUPLICATE =
            new ProjectPartiCustomException(ProjectPartiErrorCode.PARTICIPATE_DUPLICATE, ErrorInstruction.MESSAGE);

    public static final ProjectPartiCustomException PARTICIPATE_EXIST =
            new ProjectPartiCustomException(ProjectPartiErrorCode.PARTICIPATE_EXIST, ErrorInstruction.MESSAGE);

    public ProjectPartiCustomException(ProjectPartiErrorCode errorCode, ErrorInstruction errorInstruction) {
        super(errorCode, errorInstruction);
    }
}

