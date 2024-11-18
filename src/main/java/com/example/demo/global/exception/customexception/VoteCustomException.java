package com.example.demo.global.exception.customexception;

import com.example.demo.constant.ErrorInstruction;
import com.example.demo.global.exception.errorcode.ErrorCode;
import com.example.demo.global.exception.errorcode.VoteErrorCode;

public class VoteCustomException extends CustomExceptionWithInstruct {

    public static final VoteCustomException VOTE_DUPLICATE =
            new VoteCustomException(VoteErrorCode.VOTE_DUPLICATE, ErrorInstruction.MESSAGE);

    public static final VoteCustomException VOTE_NOT_ALLOWED =
            new VoteCustomException(VoteErrorCode.VOTE_NOT_ALLOWED, ErrorInstruction.MESSAGE);

    public static final VoteCustomException VOTE_TARGET_NOT_ALLOWED =
            new VoteCustomException(VoteErrorCode.VOTE_TARGET_NOT_ALLOWED, ErrorInstruction.MESSAGE);

    public static final VoteCustomException VOTE_NOT_ALLOWED_YET =
            new VoteCustomException(VoteErrorCode.VOTE_NOT_ALLOWED_YET, ErrorInstruction.MESSAGE);

    public static final VoteCustomException VOTE_INSUFF_VOTERS =
            new VoteCustomException(VoteErrorCode.VOTE_INSUFF_VOTERS, ErrorInstruction.MESSAGE);

    public static final VoteCustomException VOTE_EARLY_FW =
            new VoteCustomException(VoteErrorCode.VOTE_EARLY_FW, ErrorInstruction.MESSAGE);

    public static final VoteCustomException VOTE_EXIST_FW =
            new VoteCustomException(VoteErrorCode.VOTE_EXIST_FW, ErrorInstruction.MESSAGE);

    public VoteCustomException(ErrorCode errorCode,ErrorInstruction errorInstruction) {
        super(errorCode, errorInstruction);
    }
}
