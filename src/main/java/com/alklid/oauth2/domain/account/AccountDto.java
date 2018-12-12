package com.alklid.oauth2.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
