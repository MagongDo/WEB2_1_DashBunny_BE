/*
package com.devcourse.web2_1_dashbunny_be.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CustomWebMvcConfigurer implements WebMvcConfigurer {

        private final ObjectMapper objectMapper;

        public CustomWebMvcConfigurer(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Bean
        public MultipartJackson2HttpMessageConverter multipartConverter() {
            return new MultipartJackson2HttpMessageConverter(objectMapper);
        }
    }

*/

