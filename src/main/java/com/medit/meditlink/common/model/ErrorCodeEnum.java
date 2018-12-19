package com.medit.meditlink.common.model;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

public class ErrorCodeEnum {

    private interface ErrorCode {
        public String getCode();
        public String getMsg();
    }

    public static enum ResponseEntityError {

        METHOD_NOT_ALLOWED("method_not_allowed", "method_not_allowed"),
        UNSUPPORTED_MEDIA_TYPE("unsupported_media_type", "unsupported_media_type"),
        NOT_ACCEPTABLE("not_acceptable", "not_acceptable"),
        INTERNAL_SERVER_ERROR("internal_server_error", "internal_server_error"),
        BAD_REQUEST("bad_request", "bad_request"),
        NOT_FOUND("not_found", "not_found"),
        SERVICE_UNAVAILABLE("service_unavailable", "service_unavailable");

        private String code;
        private String msg;

        ResponseEntityError(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return this.code;
        }

        public String getMsg(String... args) {
            return this.msg + " [" + String.join(",", args) + "]";
        }

        public static String getCode(Exception ex) {
            if (ex instanceof HttpRequestMethodNotSupportedException) {
                return METHOD_NOT_ALLOWED.getCode();
            }
            else if (ex instanceof HttpMediaTypeNotSupportedException) {
                return UNSUPPORTED_MEDIA_TYPE.getCode();
            }
            else if (ex instanceof HttpMediaTypeNotAcceptableException) {
                return NOT_ACCEPTABLE.getCode();
            }
            else if (ex instanceof MissingPathVariableException) {
                return INTERNAL_SERVER_ERROR.getCode();
            }
            else if (ex instanceof MissingServletRequestParameterException) {
                return BAD_REQUEST.getCode();
            }
            else if (ex instanceof ServletRequestBindingException) {
                return BAD_REQUEST.getCode();
            }
            else if (ex instanceof ConversionNotSupportedException) {
                return INTERNAL_SERVER_ERROR.getCode();
            }
            else if (ex instanceof TypeMismatchException) {
                return BAD_REQUEST.getCode();
            }
            else if (ex instanceof HttpMessageNotReadableException) {
                return BAD_REQUEST.getCode();
            }
            else if (ex instanceof HttpMessageNotWritableException) {
                return INTERNAL_SERVER_ERROR.getCode();
            }
            else if (ex instanceof MethodArgumentNotValidException) {
                return BAD_REQUEST.getCode();
            }
            else if (ex instanceof MissingServletRequestPartException) {
                return BAD_REQUEST.getCode();
            }
            else if (ex instanceof BindException) {
                return BAD_REQUEST.getCode();
            }
            else if (ex instanceof NoHandlerFoundException) {
                return NOT_FOUND.getCode();
            }
            else if (ex instanceof AsyncRequestTimeoutException) {
                return SERVICE_UNAVAILABLE.getCode();
            }
            else {
                return INTERNAL_SERVER_ERROR.getCode();
            }
        }
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