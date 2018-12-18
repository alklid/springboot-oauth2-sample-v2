package com.medit.meditlink.common.model;

public class ErrorCodeEnum {

    private interface ErrorCode {
        public String getCode();
        public String getMsg();
    }

    public static enum GlobalError {

        UNKNOWN_EXCEPTION("unknown_exception", "unknown_exception"),
        INVALID_API_VERSION("invalid_api_version", "invalid_api_version"),
        ACCESS_DENIED("access_denied", "access_denied"),
        BAD_REQUEST("bad_request", "bad_request");

        private String code;
        private String msg;

        GlobalError(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return this.code;
        }

        public String getMsg(String... args) {
            return this.msg + " [" + String.join(",", args) + "]";
        }
    }


    public static enum UserError {

        NOT_FOUND("not_found", "not_found");

        private String code;
        private String msg;

        UserError(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return this.code;
        }

        public String getMsg(String... args) {
            return this.msg + " [" + String.join(",", args) + "]";
        }
    }

}