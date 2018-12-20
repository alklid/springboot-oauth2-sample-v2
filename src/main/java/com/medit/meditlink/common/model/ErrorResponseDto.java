package com.medit.meditlink.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;

public class ErrorResponseDto {

    /*
    * TODO Error형식을 아래와 같이 변경해야 함
    *    {
    *      "timestamp": "2018-12-19T11:58:34.129+0000",
    *      "status": 404,
    *      "error": "Not Found",
    *      "message": "No handler found for GET /v1/users/alklid@sample.com",
    *      "path": "/v1/users/alklid@sample.com"
    *    }
    */

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
