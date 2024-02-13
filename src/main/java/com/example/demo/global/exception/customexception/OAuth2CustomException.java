package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.OAuth2ErrorCode;

public class OAuth2CustomException extends CustomException{

    public static final OAuth2CustomException NOT_FOUND_OAUTH_PRINCIPAL =
            new OAuth2CustomException(OAuth2ErrorCode.NOT_FOUND_OAUTH_PRINCIPAL);

    public OAuth2CustomException(OAuth2ErrorCode oAuth2ErrorCode) {
        super(oAuth2ErrorCode);
    }
}
