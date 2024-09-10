package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.ProjectMemberErrorCode;

public class ProjectMemberCustomException extends CustomException {

    public static final ProjectMemberCustomException NOT_FOUND_PROJECT_MEMBER =
            new ProjectMemberCustomException(ProjectMemberErrorCode.NOT_FOUND_PROJECT_MEMBER);
    public static final ProjectMemberCustomException NO_OTHER_PROJECT_MANAGER =
            new ProjectMemberCustomException(ProjectMemberErrorCode.NO_OTHER_PROJECT_MANAGER);

    public ProjectMemberCustomException(ProjectMemberErrorCode projectMemberErrorCode) {
        super(projectMemberErrorCode);
    }
}
