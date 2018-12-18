package com.medit.meditlink.common.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseDto {

    @Data
    public static class Error {
        private LocalDateTime timestamp = LocalDateTime.now();
        private int status;
        private String code;
        private String path;
        private List<String> errors;
    }

    @Data
    public static class Response {
        private LocalDateTime timestamp = LocalDateTime.now();
        private int status;
        private String code;
        private String path;
    }
}
