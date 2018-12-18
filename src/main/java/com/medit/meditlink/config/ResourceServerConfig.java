package com.medit.meditlink.config;

import com.medit.meditlink.domain.oauth.custom.CustomAuthenticationEntryPoint;
import com.medit.meditlink.domain.oauth.custom.CustomOAuthExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "oauth_test_resources_id";

    @Autowired
    private CustomOAuthExceptionHandler customOAuthExceptionHandler;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // stateless : Flag to indicate that only token-based authentication is allowed on these resources.
        // accessDeniedHandler : ouath exception 반환형태를 통일시키기 위함
        // TODO stateless 변경에 따른 동작 확인 필요
        resources.resourceId(RESOURCE_ID).stateless(false);
        resources.authenticationEntryPoint(customAuthenticationEntryPoint);
        resources.accessDeniedHandler(customOAuthExceptionHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // TODO HTTPS로 요청을 받아야만 할 경우에 대한 설정 필요
        //@formatter:off
        http
            .authorizeRequests()
            .antMatchers("/version").permitAll()
            .anyRequest().authenticated();
        //@formatter:on
    }
}
