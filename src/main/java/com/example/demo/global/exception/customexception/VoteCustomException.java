package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.ErrorCode;
import com.example.demo.global.exception.errorcode.VoteErrorCode;

public class VoteCustomException extends CustomException {

    public static final VoteCustomException VOTE_DUPLICATE =
            new VoteCustomException(VoteErrorCode.VOTE_DUPLICATE);

    public static final VoteCustomException VOTE_NOT_ALLOWED =
            new VoteCustomException(VoteErrorCode.VOTE_NOT_ALLOWED);

    public static final VoteCustomException VOTE_NOT_ALLOWED_YET =
            new VoteCustomException(VoteErrorCode.VOTE_NOT_ALLOWED_YET);

    public static final VoteCustomException VOTE_INSUFF_VOTERS =
            new VoteCustomException(VoteErrorCode.VOTE_INSUFF_VOTERS);

    public static final VoteCustomException VOTE_EARLY_FW =
            new VoteCustomException(VoteErrorCode.VOTE_EARLY_FW);

    public static final VoteCustomException VOTE_EXIST_FW =
            new VoteCustomException(VoteErrorCode.VOTE_EXIST_FW);

    public VoteCustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
