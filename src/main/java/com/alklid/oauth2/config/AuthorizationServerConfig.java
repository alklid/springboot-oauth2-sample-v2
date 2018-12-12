package com.alklid.oauth2.config;

import com.alklid.oauth2.domain.oauth.custom.CustomOAuthExceptionHandler;
import com.alklid.oauth2.domain.oauth.custom.CustomRandomValueAuthorizationCodeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("oauthClientPasswordEncoder")
    private PasswordEncoder oauthClientPasswordEncoder;

    @Autowired
    private CustomRandomValueAuthorizationCodeServices customRandomValueAuthorizationCodeServices;

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // Client Details 정보는 jdbc를 통해 dataSource로부터 가져오도록 설정
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // oauth URL 접근에 대한 보안설정
        // tokenKeyAccsss : /oauth/token_key = permitAll(), 모두 허용
        // checkTokenAccess : /oauth/check_token = isAuthenticated(), 인증받은 token만 허용
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(oauthClientPasswordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // tokenStore : access_token, refresh_token store
        // approvalStore : authorization_code 방식에서 approval관리를 위해 사용하는 store
        // userDetailService : 사용자 정보를 관리하는 service와 연결
        // authorizationCodeServices : authorization_cde 방식에서 code 값을 변경시키기 위해 사용
        // exceptionTranslator : oauth에 반환하는 exception형태도 다른 API들과 통일시키기 위해 사용
        //@formatter:off
        endpoints
                .tokenStore(tokenStore())
                .approvalStore(approvalStore())
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(customRandomValueAuthorizationCodeServices)
                .exceptionTranslator(
                        exception -> {
                            if (exception instanceof OAuth2Exception) {
                                OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
                                return CustomOAuthExceptionHandler.AuthTokenException.getException(oAuth2Exception);
                            } else {
                                throw exception;
                            }
                        }
                );
        //@formatter:on
    }




}
