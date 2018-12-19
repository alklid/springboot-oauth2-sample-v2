package com.medit.meditlink.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class ErrorResponseDto {

    @Data
    public static class Error {
        private Instant timestamp = Instant.now();
        private int status;
        private String code;
        private String path;
        private List<String> errors;
    }

    @Data
    public static class Response {
        private Instant timestamp;
        private int status;
        private String code;
        private String path;
    }
}
