package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.BoardErrorCode;

public class BoardCustomException extends CustomException {

    public static final BoardCustomException NOT_FOUND_BOARD =
            new BoardCustomException(BoardErrorCode.NOT_FOUND_BOARD);

    public static final BoardCustomException NO_PERMISSION_TO_EDIT_OR_DELETE =
            new BoardCustomException(BoardErrorCode.NO_PERMISSION_TO_EDIT_OR_DELETE);

    public BoardCustomException(BoardErrorCode boardErrorCode) {
        super(boardErrorCode);
    }
}
