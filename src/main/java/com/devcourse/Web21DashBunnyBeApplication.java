package com.devcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Web21DashBunnyBeApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Web21DashBunnyBeApplication.class);
        app.setAdditionalProfiles("prod");
        app.run(args);
    }

}
// .