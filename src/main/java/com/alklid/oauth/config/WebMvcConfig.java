package com.alklid.oauth.config;

import com.alklid.oauth.common.custom.VersionCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private VersionCheckInterceptor versionCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(versionCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/oauth/**"); // /oauth 인증은 예외처리
    }
}