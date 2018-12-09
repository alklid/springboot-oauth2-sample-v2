package com.alklid.oauth2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class Application {

    // 서버 구동전에 TimeZone을 UTC Zero로 변경
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // TODO 공통 configure쪽으로 이동필요
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
