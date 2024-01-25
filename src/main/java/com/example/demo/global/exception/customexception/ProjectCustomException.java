package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.ProjectErrorCode;

public class ProjectCustomException extends CustomException {

    public static final ProjectCustomException NOT_FOUND_PROJECT =
            new ProjectCustomException(ProjectErrorCode.NOT_FOUND_PROJECT);

    public static final ProjectCustomException NO_PERMISSION_TO_TASK =
            new ProjectCustomException(ProjectErrorCode.NO_PERMISSION_TO_TASK);

    public ProjectCustomException(ProjectErrorCode projectErrorCode) {
        super(projectErrorCode);
    }
}
