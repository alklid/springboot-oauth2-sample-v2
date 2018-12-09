package com.alklid.oauth2.domain.version;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

    @Autowired
    private Environment env;

    public VersionEntity getVersion() {

        VersionEntity version = new VersionEntity();
        version.setBuild_version(env.getProperty("api.build.version", ""));
        version.setRelease_version(env.getProperty("api.release.version", ""));

        return version;
    }
}
