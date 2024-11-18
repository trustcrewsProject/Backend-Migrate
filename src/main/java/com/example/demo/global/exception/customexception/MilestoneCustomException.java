package com.example.demo.global.exception.customexception;

import com.example.demo.constant.ErrorInstruction;
import com.example.demo.global.exception.errorcode.MilestoneErrorCode;

public class MilestoneCustomException extends CustomExceptionWithInstruct {

    public static final MilestoneCustomException NOT_FOUND_MILESTONE =
            new MilestoneCustomException(MilestoneErrorCode.NOT_FOUND_MILESTONE, ErrorInstruction.MESSAGE);


    public static final MilestoneCustomException NOT_ALLOWED_CD =
            new MilestoneCustomException(MilestoneErrorCode.NOT_ALLOWED_CD, ErrorInstruction.MESSAGE);

    public static final MilestoneCustomException CREATE_EXCEEDED_MS =
            new MilestoneCustomException(MilestoneErrorCode.CREATE_EXCEEDED_MS, ErrorInstruction.MESSAGE);

    public MilestoneCustomException(MilestoneErrorCode milestoneErrorCode, ErrorInstruction errorInstruction) {
        super(milestoneErrorCode, errorInstruction);
    }
}
