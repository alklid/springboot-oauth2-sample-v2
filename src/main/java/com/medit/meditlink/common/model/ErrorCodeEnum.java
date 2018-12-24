package com.medit.meditlink.common.model;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
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

    public static enum GlobalError {
        UNKNOWN_EXCEPTION("unknown_exception", "unknown_exception"),
        INVALID_API_VERSION("invalid_api_version", "invalid_api_version"),
        INVALID_SCHEMA_VERSION("invalid_schema_version", "invalid_schema_version"),
        ACCESS_DENIED("access_denied", "access_denied"),
        BAD_REQUEST("bad_request", "bad_request"),
        NO_HANDLER_FOUND("no_handler_found", "no_handler_found");

        private String error;
        private String message;

        GlobalError(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return this.error;
        }

        public String getMessage() {
            return message;
        }

        public String getDetailMessage(String... args) {
            return this.message + " [" + String.join(",", args) + "]";
        }
    }


    public static enum UserError {
        NOT_FOUND("not_found", "not_found");

        private String error;
        private String message;

        UserError(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return this.error;
        }

        public String getMessage() {
            return this.message;
        }

        public String getDetailMessage(String... args) {
            return this.message + " [" + String.join(",", args) + "]";
        }
    }


    public static enum ResponseEntityError {
        METHOD_NOT_ALLOWED("method_not_allowed", "method_not_allowed"),
        UNSUPPORTED_MEDIA_TYPE("unsupported_media_type", "unsupported_media_type"),
        NOT_ACCEPTABLE("not_acceptable", "not_acceptable"),
        INTERNAL_SERVER_ERROR("internal_server_error", "internal_server_error"),
        BAD_REQUEST("bad_request", "bad_request"),
        NOT_FOUND("not_found", "not_found"),
        SERVICE_UNAVAILABLE("service_unavailable", "service_unavailable");

        private String error;
        private String message;

        ResponseEntityError(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return this.error;
        }

        public String getMessage() {
            return this.message;
        }

        public String getDetailMessage(String... args) {
            return this.message + " [" + String.join(",", args) + "]";
        }

        public static String getError(Exception ex) {
            if (ex instanceof HttpRequestMethodNotSupportedException) {
                return METHOD_NOT_ALLOWED.getError();
            }
            else if (ex instanceof HttpMediaTypeNotSupportedException) {
                return UNSUPPORTED_MEDIA_TYPE.getError();
            }
            else if (ex instanceof HttpMediaTypeNotAcceptableException) {
                return NOT_ACCEPTABLE.getError();
            }
            else if (ex instanceof MissingPathVariableException) {
                return INTERNAL_SERVER_ERROR.getError();
            }
            else if (ex instanceof MissingServletRequestParameterException) {
                return BAD_REQUEST.getError();
            }
            else if (ex instanceof ServletRequestBindingException) {
                return BAD_REQUEST.getError();
            }
            else if (ex instanceof ConversionNotSupportedException) {
                return INTERNAL_SERVER_ERROR.getError();
            }
            else if (ex instanceof TypeMismatchException) {
                return BAD_REQUEST.getError();
            }
            else if (ex instanceof HttpMessageNotReadableException) {
                return BAD_REQUEST.getError();
            }
            else if (ex instanceof HttpMessageNotWritableException) {
                return INTERNAL_SERVER_ERROR.getError();
            }
            else if (ex instanceof MethodArgumentNotValidException) {
                return BAD_REQUEST.getError();
            }
            else if (ex instanceof MissingServletRequestPartException) {
                return BAD_REQUEST.getError();
            }
            else if (ex instanceof BindException) {
                return BAD_REQUEST.getError();
            }
            else if (ex instanceof NoHandlerFoundException) {
                return NOT_FOUND.getError();
            }
            else if (ex instanceof AsyncRequestTimeoutException) {
                return SERVICE_UNAVAILABLE.getError();
            }
            else {
                return INTERNAL_SERVER_ERROR.getError();
            }
        }

        public static String getMessage(Exception ex) {
            if (ex instanceof HttpRequestMethodNotSupportedException) {
                return METHOD_NOT_ALLOWED.getMessage();
            }
            else if (ex instanceof HttpMediaTypeNotSupportedException) {
                return UNSUPPORTED_MEDIA_TYPE.getMessage();
            }
            else if (ex instanceof HttpMediaTypeNotAcceptableException) {
                return NOT_ACCEPTABLE.getMessage();
            }
            else if (ex instanceof MissingPathVariableException) {
                return INTERNAL_SERVER_ERROR.getMessage();
            }
            else if (ex instanceof MissingServletRequestParameterException) {
                return BAD_REQUEST.getMessage();
            }
            else if (ex instanceof ServletRequestBindingException) {
                return BAD_REQUEST.getMessage();
            }
            else if (ex instanceof ConversionNotSupportedException) {
                return INTERNAL_SERVER_ERROR.getMessage();
            }
            else if (ex instanceof TypeMismatchException) {
                return BAD_REQUEST.getMessage();
            }
            else if (ex instanceof HttpMessageNotReadableException) {
                return BAD_REQUEST.getMessage();
            }
            else if (ex instanceof HttpMessageNotWritableException) {
                return INTERNAL_SERVER_ERROR.getMessage();
            }
            else if (ex instanceof MethodArgumentNotValidException) {
                return BAD_REQUEST.getMessage();
            }
            else if (ex instanceof MissingServletRequestPartException) {
                return BAD_REQUEST.getMessage();
            }
            else if (ex instanceof BindException) {
                return BAD_REQUEST.getMessage();
            }
            else if (ex instanceof NoHandlerFoundException) {
                return NOT_FOUND.getMessage();
            }
            else if (ex instanceof AsyncRequestTimeoutException) {
                return SERVICE_UNAVAILABLE.getMessage();
            }
            else {
                return INTERNAL_SERVER_ERROR.getMessage();
            }
        }
    }

}