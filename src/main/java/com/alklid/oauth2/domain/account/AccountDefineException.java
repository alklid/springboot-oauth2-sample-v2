package com.alklid.oauth2.domain.account;

import com.alklid.oauth2.common.DefineException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

public class AccountDefineException {

    @AllArgsConstructor
    public static class Duplicated extends RuntimeException {
        @Getter
        String email;

        @Getter
        HttpServletRequest httpRequest;

        public String getPath() {
            return DefineException.getPath(this.httpRequest);
        }
    }

    @AllArgsConstructor
    public static class NotFound extends RuntimeException {
        @Getter
        Long users_sid;

        @Getter
        String email;

        @Getter
        HttpServletRequest httpRequest;

        public String getPath() {
            return DefineException.getPath(this.httpRequest);
        }
    }

    @AllArgsConstructor
    public static class invalidPwd extends RuntimeException {
        @Getter
        String email;

        @Getter
        HttpStatus status;

        @Getter
        HttpServletRequest httpRequest;

        public String getPath() {
            return DefineException.getPath(this.httpRequest);
        }
    }

    @AllArgsConstructor
    public static class samePwd extends RuntimeException {
        @Getter
        String email;

        @Getter
        HttpServletRequest httpRequest;

        public String getPath() {
            return DefineException.getPath(this.httpRequest);
        }
    }

    @AllArgsConstructor
    public static class UserSidNotMatched extends RuntimeException {
    	@Getter
        Long users_sid;

        @Getter
        HttpServletRequest httpRequest;

        public String getPath() {
            return DefineException.getPath(this.httpRequest);
        }
    }

    @AllArgsConstructor
    public static class UserEmailNotMatched extends RuntimeException {
        @Getter
        String email;

        @Getter
        HttpServletRequest httpRequest;

        public String getPath() {
            return DefineException.getPath(this.httpRequest);
        }
    }
}
