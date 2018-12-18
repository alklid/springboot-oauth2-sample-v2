package com.medit.meditlink.domain.oauth.custom;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomRandomValueAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private RandomValueStringGenerator generator = new RandomValueStringGenerator(32);
    protected final ConcurrentHashMap<String, OAuth2Authentication> authorizationCodeStore = new ConcurrentHashMap<String, OAuth2Authentication>();

    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        this.authorizationCodeStore.put(code, authentication);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        OAuth2Authentication auth = this.authorizationCodeStore.remove(code);
        return auth;
    }

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = generator.generate();
        store(code, authentication);
        return code;
    }
}

