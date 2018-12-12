package com.alklid.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder oauthClientPasswordEncoder() {
        // TODO DefaultEncoder 변경 및 strength 변경시 Custom 필요함
        //return new BCryptPasswordEncoder(4); -- default strength(4)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public PasswordEncoder userPasswordEncoder() {
        //return new BCryptPasswordEncoder(8);
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
            .csrf().disable()
            .requestMatchers()
            .antMatchers("/login", "/oauth/authorize")
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin().permitAll();
        //@formatter:on
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    public void globalConfigure(AuthenticationManagerBuilder auth) throws Exception {
        //@formatter:off
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(userPasswordEncoder());
        //@formatter:on

        /*
        // in-Memory use
        //@formatter:off
        PasswordEncoder passwordEncoder = userPasswordEncoder();
        auth
            .inMemoryAuthentication()
            .passwordEncoder(userPasswordEncoder())
            .withUser("alklid").password(passwordEncoder.encode("1111")).roles("MANAGE")
            .and()
            .withUser("alklid2").password(passwordEncoder.encode("2222")).roles("MANAGE");
        //@formatter:on
        */
    }

}
