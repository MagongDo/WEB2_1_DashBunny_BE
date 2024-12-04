package com.devcourse.web2_1_dashbunny_be.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // 'uploads/profile-pictures/' 디렉토리를 '/uploads/profile-pictures/**' 경로로 매핑
    registry.addResourceHandler("/uploads/profile-pictures/**")
            .addResourceLocations("file:uploads/profile-pictures/");
  }
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000") // 허용할 프론트엔드 도메인
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
            .allowedHeaders("*") // 허용할 헤더
            .allowCredentials(true); // 쿠키 허용 여부
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
