package com.medit.meditlink.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class ErrorResponseDto {

    @Data
    @NoArgsConstructor
    public static class Error {
        private Instant timestamp = Instant.now();
        private int status;
        private String code;
        private String path;
        private List<String> errors;

        public Error(WebRequest request) {
            this.timestamp = Instant.now();
            this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
        }
    }

    @Data
    public static class Response {
        private Instant timestamp;
        private int status;
        private String code;
        private String path;
    }
}
