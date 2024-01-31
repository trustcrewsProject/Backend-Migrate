package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.ProjectMemberAuthErrorCode;

public class ProjectMemberAuthCustomException extends CustomException {

    public static final ProjectMemberAuthCustomException NOT_FOUND_PROJECT_MEMBER_AUTH =
            new ProjectMemberAuthCustomException(
                    ProjectMemberAuthErrorCode.NOT_FOUND_PROJECT_MEMBER_AUTH);

    public static final ProjectMemberAuthCustomException INSUFFICIENT_PROJECT_AUTH =
            new ProjectMemberAuthCustomException(ProjectMemberAuthErrorCode.INSUFFICIENT_PROJECT_AUTH);

    public ProjectMemberAuthCustomException(ProjectMemberAuthErrorCode projectMemberAuthErrorCode) {
        super(projectMemberAuthErrorCode);
    }
}
