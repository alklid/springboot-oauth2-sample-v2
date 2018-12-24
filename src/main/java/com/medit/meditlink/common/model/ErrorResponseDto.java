package com.medit.meditlink.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;

public class ErrorResponseDto {

    @Data
    @NoArgsConstructor
    public static class Error {
        private Instant timestamp = Instant.now();
        private int status;
        private String error;
        private String message;
        private String path;
        private List<String> addInfo;

        public Error(WebRequest request) {
            this.timestamp = Instant.now();
            this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
        }
    }

    @Data
    public static class Response {
        private Instant timestamp = Instant.now();
        private int status;
        private String error;
        private String message;
        private String path;
    }
}
