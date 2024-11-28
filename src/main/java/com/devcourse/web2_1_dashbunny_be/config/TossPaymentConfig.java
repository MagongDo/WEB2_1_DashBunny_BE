package com.devcourse.web2_1_dashbunny_be.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Getter
public class TossPaymentConfig {

    @Value("${toss.payment.api-base-url}")
    private String apiBaseUrl;

    @Value("${toss.payment.secret-key}")
    private String secretKey;

    @Value("${toss.payment.client-key}")
    private String clientKey;

    @Value("${toss.payment.success-url}")
    private String successUrl;

    @Value("${toss.payment.fail-url}")
    private String failUrl;


}