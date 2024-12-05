//package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon;
//
//import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
//import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.service.UserCouponService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.Collections;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@SpringBootTest
//public class FirstComeCouponConcurrencyTest {
//
//    @Autowired
//    private UserCouponService userCouponService;
//
//    private final int THREAD_COUNT = 100;
//
//    @BeforeEach
//    public void setup() {
//        // 테스트용 인증 정보 설정
//        SecurityContextHolder.getContext().setAuthentication(
//                new UsernamePasswordAuthenticationToken("testUser", null, Collections.emptyList())
//        );
//    }
//
//    @Test
//    public void testConcurrentFirstComeCouponDownload() throws InterruptedException {
//        Long couponId = 1L;
//        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
//        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
//
//        for (int i = 0; i < THREAD_COUNT; i++) {
//            executorService.execute(() -> {
//                try {
//                    // 각 스레드에 독립적인 SecurityContext 설정
//                    SecurityContext context = SecurityContextHolder.createEmptyContext();
//                    context.setAuthentication(
//                            new UsernamePasswordAuthenticationToken("testUser", null, Collections.emptyList())
//                    );
//                    SecurityContextHolder.setContext(context);
//
//                    userCouponService.downloadCoupon(couponId, IssuedCouponType.ADMIN,cu);
//                    System.out.println("쿠폰 다운로드 성공");
//                } catch (Exception e) {
//                    System.err.println("에러 발생: " + e.getMessage());
//                } finally {
//                    latch.countDown();
//                    // 테스트 후 SecurityContext 초기화
//                    SecurityContextHolder.clearContext();
//                }
//            });
//        }
//
//        latch.await();
//        executorService.shutdown();
//    }
//}
