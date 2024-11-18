package com.example.demo.global.exception.customexception;

import com.example.demo.constant.ErrorInstruction;
import com.example.demo.global.exception.errorcode.ProjectMemberErrorCode;

public class ProjectMemberCustomException extends CustomExceptionWithInstruct {

    public static final ProjectMemberCustomException NOT_FOUND_PROJECT_MEMBER =
            new ProjectMemberCustomException(ProjectMemberErrorCode.NOT_FOUND_PROJECT_MEMBER, ErrorInstruction.NONE);
    public static final ProjectMemberCustomException NO_OTHER_PROJECT_MANAGER =
            new ProjectMemberCustomException(ProjectMemberErrorCode.NO_OTHER_PROJECT_MANAGER, ErrorInstruction.MESSAGE);

    public ProjectMemberCustomException(ProjectMemberErrorCode projectMemberErrorCode, ErrorInstruction errorInstruction) {
        super(projectMemberErrorCode, errorInstruction);
    }
}
