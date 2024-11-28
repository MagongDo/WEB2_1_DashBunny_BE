package com.devcourse.web2_1_dashbunny_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 'uploads/profile-pictures/' 디렉토리를 '/uploads/profile-pictures/**' 경로로 매핑
        registry.addResourceHandler("/uploads/profile-pictures/**")
                .addResourceLocations("file:uploads/profile-pictures/");
    }

//    private final MultipartJackson2HttpMessageConverter multipartJackson2HttpMessageConverter;
//
//    public WebConfig(MultipartJackson2HttpMessageConverter multipartJackson2HttpMessageConverter) {
//        this.multipartJackson2HttpMessageConverter = multipartJackson2HttpMessageConverter;
//    }
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(multipartJackson2HttpMessageConverter); // 커스텀 컨버터 추가
//    }
}
