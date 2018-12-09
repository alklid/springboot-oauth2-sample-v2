package com.alklid.oauth2.domain.version;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;

@Data
public class VersionEntity {

    private String build_version;
    private String release_version;

}
