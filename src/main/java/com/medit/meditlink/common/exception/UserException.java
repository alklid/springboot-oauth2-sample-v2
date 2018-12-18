package com.medit.meditlink.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserException extends GlobalException {

    @AllArgsConstructor
    public static class NotFound extends RuntimeException {
        @Getter
        Long users_sid;

        @Getter
        String email;
    }
}
