package com.example.demo.global.exception.customexception;

import com.example.demo.constant.ErrorInstruction;
import com.example.demo.global.exception.errorcode.TokenErrorCode;

public class TokenCustomException extends CustomExceptionWithInstruct {

    public static final TokenCustomException NON_ACCESS_TOKEN =
            new TokenCustomException(TokenErrorCode.NON_ACCESS_TOKEN, ErrorInstruction.REDIRECT);

    public static final TokenCustomException WRONG_TYPE_SIGNATURE =
            new TokenCustomException(TokenErrorCode.WRONG_TYPE_SIGNATURE, ErrorInstruction.REDIRECT);

    public static final TokenCustomException WRONG_TYPE_TOKEN =
            new TokenCustomException(TokenErrorCode.WRONG_TYPE_TOKEN, ErrorInstruction.REDIRECT);

    public static final TokenCustomException EXPIRED_TOKEN =
            new TokenCustomException(TokenErrorCode.EXPIRED_TOKEN, ErrorInstruction.NONE);

    public static final TokenCustomException MALFORMED_TOKEN =
            new TokenCustomException(TokenErrorCode.MALFORMED_TOKEN, ErrorInstruction.REDIRECT);

    public static final TokenCustomException NO_REFRESH_TOKEN =
            new TokenCustomException(TokenErrorCode.NO_REFRESH_TOKEN, ErrorInstruction.NONE);

    public static final TokenCustomException INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE =
            new TokenCustomException(
                    TokenErrorCode.INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE, ErrorInstruction.REDIRECT);

    public static final TokenCustomException INVALID_REFRESH_TOKEN =
            new TokenCustomException(TokenErrorCode.INVALID_REFRESH_TOKEN, ErrorInstruction.REDIRECT);

    public static final TokenCustomException REFRESH_TOKEN_NOT_FOUND =
            new TokenCustomException(TokenErrorCode.REFRESH_TOKEN_NOT_FOUND, ErrorInstruction.REDIRECT);

    public TokenCustomException(TokenErrorCode tokenErrorCode, ErrorInstruction errorInstruction) {
        super(tokenErrorCode, errorInstruction);
    }
}
