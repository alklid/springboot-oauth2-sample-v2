package com.medit.meditlink.domain.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.medit.meditlink.common.Constant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

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
