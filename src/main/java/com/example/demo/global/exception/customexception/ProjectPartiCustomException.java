package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.ProjectPartiErrorCode;

public class ProjectPartiCustomException extends CustomException {

    public static final ProjectPartiCustomException PARTICIPATE_NOT_ALLOWED =
            new ProjectPartiCustomException(ProjectPartiErrorCode.PARTICIPATE_NOT_ALLOWED);

    public static final ProjectPartiCustomException PARTICIPATE_DUPLICATE =
            new ProjectPartiCustomException(ProjectPartiErrorCode.PARTICIPATE_DUPLICATE);

    public ProjectPartiCustomException(ProjectPartiErrorCode errorCode){
        super(errorCode);
    }
}

