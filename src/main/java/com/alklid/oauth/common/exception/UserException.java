package com.alklid.oauth.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserException extends GlobalException {

    @AllArgsConstructor
    public static class NotFound extends RuntimeException {
        @Getter
        Long users_sid;

        @Getter
        String email;
    }
}
