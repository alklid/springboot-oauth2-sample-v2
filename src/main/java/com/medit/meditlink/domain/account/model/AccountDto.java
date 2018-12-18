package com.medit.meditlink.domain.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

public class AccountDto {

    @Data
    public static class Response {
        private Long sid;
        private String email;
        private String name;
        private String permissions;

        @JsonProperty("created_at")
        private LocalDateTime createdAt;

        @JsonProperty("last_modified_at")
        private LocalDateTime lastModifiedAt;
    }
}
