package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.PageNationErrorCode;

public class PageNationCustomException extends CustomException {

    public static final PageNationCustomException INVALID_PAGE_NUMBER =
            new PageNationCustomException(PageNationErrorCode.INVALID_PAGE_NUMBER);

    public PageNationCustomException(PageNationErrorCode pageNationErrorCode) {
        super(pageNationErrorCode);
    }
}
