package com.alklid.oauth.domain.user.model;

import com.alklid.oauth.common.Constant;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Instant;

public class UserDto {

    @Data
    public static class Response {
        private String schemaVersion = Constant.SchemaVersion.SCHEMA_1;
        private Long sid;
        private String email;
        private String name;
        private String permissions;
        private Instant createdAt;
        private Instant lastModifiedAt;
        private Instant dateUpdate;
    }

    @Data
    public static class Update {
        @NotBlank
        private String name;
        private Instant dateUpdate;
    }
}
