package com.alklid.oauth2.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseBean {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;
    private String path;
}
