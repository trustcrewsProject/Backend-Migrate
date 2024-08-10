package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.ErrorCode;
import com.example.demo.global.exception.errorcode.VoteErrorCode;

public class VoteCustomException extends CustomException {

    public static final VoteCustomException VOTE_DUPLICATE =
            new VoteCustomException(VoteErrorCode.VOTE_DUPLICATE);

    public static final VoteCustomException VOTE_NOT_ALLOWED =
            new VoteCustomException(VoteErrorCode.VOTE_NOT_ALLOWED);

    public VoteCustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
