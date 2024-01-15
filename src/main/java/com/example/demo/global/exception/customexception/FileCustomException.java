package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.FileErrorCode;

public class FileCustomException extends CustomException{

    public static final FileCustomException INVALID_IMAGE_TYPE =
            new FileCustomException(FileErrorCode.INVALID_IMAGE_TYPE);

    public static final FileCustomException FILE_SIZE_EXCEEDED =
            new FileCustomException(FileErrorCode.FILE_SIZE_EXCEEDED);

    public FileCustomException(FileErrorCode fileErrorCode) {
        super(fileErrorCode);
    }
}
