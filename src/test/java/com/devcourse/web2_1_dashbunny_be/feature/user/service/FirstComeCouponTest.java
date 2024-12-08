package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class FirstComeCouponTest {

    private final String BASE_URL = "http://localhost:8080/api/user/coupon/download/first-come/";

    @Autowired
    private RestTemplate restTemplate;



    @Test
    public void testConcurrentRequestsWithSingleAccessToken() throws InterruptedException {
        Long couponId = 21L; // 테스트할 쿠폰 ID
        int concurrentUsers = 10; // 동시 요청 사용자 수
        ExecutorService executor = Executors.newFixedThreadPool(concurrentUsers);

        // 로그인 후 액세스 토큰 가져오기
        String accessToken = getAccessToken("01011111111", "qawsed5@");

        for (int i = 1; i <= concurrentUsers; i++) {
            int userId = i;
            executor.submit(() -> {
                try {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + accessToken); // 단일 액세스 토큰 사용
                    headers.set("User-Id", String.valueOf(userId)); // 고유 유저 번호 부여 (예: 커스텀 헤더)

                    HttpEntity<String> request = new HttpEntity<>(headers);

                    // POST 요청 전송
                    ResponseEntity<String> response = restTemplate.postForEntity(
                            BASE_URL + couponId,
                            request,
                            String.class
                    );

                    System.out.printf("User %d: %s\n", userId, response.getBody());
                } catch (Exception e) {
                    System.err.printf("User %d failed: %s\n", userId, e.getMessage());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    private String getAccessToken(String phone, String password) {
        String loginUrl = "http://localhost:8080/api/auth/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("phone", phone);
        loginRequest.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(loginRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // 토큰 추출
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.getBody());
                return jsonNode.get("accessToken").asText();
            } else {
                throw new RuntimeException("Failed to get access token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception during login: " + e.getMessage());
        }
    }

}