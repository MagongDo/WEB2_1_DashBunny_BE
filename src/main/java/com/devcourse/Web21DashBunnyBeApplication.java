package com.devcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling // 스케줄링을 활성화
public class Web21DashBunnyBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(Web21DashBunnyBeApplication.class, args);
    }

}
