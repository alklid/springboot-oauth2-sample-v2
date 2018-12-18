package com.medit.meditlink.domain.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserDto {

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
