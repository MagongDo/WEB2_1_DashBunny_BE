package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.feature.admin.kafka.KafkaConsumerService;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.service.UserCouponService;
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

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @Autowired
    private UserCouponService userCouponService;
    @Test
    public void testConcurrentRequestsUsingDatabaseUsers() throws InterruptedException {
        Long couponId = 37L; // 테스트할 쿠폰 ID
        int concurrentUsers = 10; // 동시 요청 사용자 수
        ExecutorService executor = Executors.newFixedThreadPool(concurrentUsers);

        // 사용자 데이터
        String[][] users = {
                {"01000000001", "testPassword1"},
                {"01000000002", "testPassword2"},
                {"01000000003", "testPassword3"},
                {"01000000004", "testPassword4"},
                {"01000000005", "testPassword5"},
                {"01000000006", "testPassword6"},
                {"01000000007", "testPassword7"},
                {"01000000008", "testPassword8"},
                {"01000000009", "testPassword9"},
                {"01000000010", "testPassword10"}
        };

        Map<String, String> accessTokens = new HashMap<>();

        // 각 사용자별로 로그인 요청 보내기
        for (String[] user : users) {
            executor.submit(() -> {
                String phone = user[0];
                String password = user[1];
                try {
                    String token = getAccessToken(phone, password);
                    synchronized (accessTokens) {
                        accessTokens.put(phone, token);
                    }
                } catch (Exception e) {
                    System.err.printf("Login failed for user %s: %s\n", phone, e.getMessage());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);

        // 모든 사용자 액세스 토큰 출력 (테스트용)
        accessTokens.forEach((phone, token) -> System.out.printf("User %s: Token %s\n", phone, token));

        // 쿠폰 다운로드 요청
        ExecutorService requestExecutor = Executors.newFixedThreadPool(concurrentUsers);

        for (String[] user : users) {
            final String phone = user[0];
            requestExecutor.submit(() -> {
                try {
                    String token = accessTokens.get(phone);

                    if (token == null) {
                        System.err.printf("No token for user %s\n", phone);
                        return;
                    }

                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + token);

                    HttpEntity<String> request = new HttpEntity<>(headers);

                    ResponseEntity<String> response = restTemplate.postForEntity(
                            BASE_URL + couponId,
                            request,
                            String.class
                    );

                    System.out.printf("User %s: %s\n", phone, response.getBody());
                } catch (Exception e) {
                    System.err.printf("Request failed for user %s: %s\n", phone, e.getMessage());
                }
            });
        }

        requestExecutor.shutdown();
        requestExecutor.awaitTermination(10, TimeUnit.SECONDS);
    }

//    @Test
//    public void testConcurrentRequestsWithSingleAccessToken() throws InterruptedException {
//        Long couponId = 28L; // 테스트할 쿠폰 ID
//        int concurrentUsers = 10; // 동시 요청 사용자 수
//        ExecutorService executor = Executors.newFixedThreadPool(concurrentUsers);
//
//        // 로그인 후 액세스 토큰 가져오기
//        String accessToken = getAccessToken("01011111111", "qawsed5@");
//
//        for (int i = 1; i <= concurrentUsers; i++) {
//            int userId = i;
//            executor.submit(() -> {
//                try {
//                    HttpHeaders headers = new HttpHeaders();
//                    headers.set("Authorization", "Bearer " + accessToken); // 단일 액세스 토큰 사용
//                    headers.set("User-Id", String.valueOf(userId)); // 고유 유저 번호 부여 (예: 커스텀 헤더)
//
//                    HttpEntity<String> request = new HttpEntity<>(headers);
//
//                    // POST 요청 전송
//                    ResponseEntity<String> response = restTemplate.postForEntity(
//                            BASE_URL + couponId,
//                            request,
//                            String.class
//                    );
//
//                    System.out.printf("User %d: %s\n", userId, response.getBody());
//                } catch (Exception e) {
//                    System.err.printf("User %d failed: %s\n", userId, e.getMessage());
//                }
//            });
//        }
//
//
//        executor.shutdown();
//        executor.awaitTermination(10, TimeUnit.SECONDS);
//
//        // Kafka 메시지가 처리되었는지 검증
//        TimeUnit.SECONDS.sleep(5); // 메시지 처리를 기다림
//        long issuedCoupons = userCouponService.countIssuedCoupons(couponId);
//        System.out.printf("Issued Coupons: %d\n", issuedCoupons);
//        assert issuedCoupons <= concurrentUsers;
//    }

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