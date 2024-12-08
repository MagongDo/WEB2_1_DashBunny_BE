package com.devcourse.web2_1_dashbunny_be.feature.admin.kafka;

import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import com.devcourse.web2_1_dashbunny_be.feature.user.service.UserService;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {
  private final UserCouponService userCouponService;
  private final UserService userService;
  private final KafkaProducerService kafkaProducerService;

//  @KafkaListener(topics = "coupon-download-topic", groupId = "coupon-group")
//  public void consumeMessage(CouponRequestMessage message) {
//    log.info("Received message: {}", message);
//    // Kafka에서 수신한 메시지를 처리
//    userCouponService.downloadFirstComeCoupon(message.getUserId(), message.getCouponId());
//
  @KafkaListener(topics = "coupon-download-topic", groupId = "coupon-group", containerFactory = "kafkaListenerContainerFactory")
  public void consumeMessages(List<CouponRequestMessage> messages) {
    log.info("Received {} messages", messages.size());

    try {
      // 메시지를 UserCoupon 엔티티로 변환
      List<UserCoupon> userCoupons = messages.stream()
              .map(msg -> UserCoupon.builder()
                      .userId(msg.getUserId())
                      .couponId(msg.getCouponId())
                      .issuedCouponType(IssuedCouponType.ADMIN)
                      .usedDate(null)
                      .couponUsed(false)
                      .isExpired(false)
                      .build()
              )
              .collect(Collectors.toList());

      // 배치로 DB에 저장
      userCouponService.saveAll(userCoupons);
      log.info("Batch saved {} user coupons", userCoupons.size());
    } catch (Exception e) {
      log.error("DB 저장 실패: {}", e.getMessage());
      // 실패한 메시지를 DLQ로 전송
      for (CouponRequestMessage message : messages) {
        kafkaProducerService.sendToDLQ(message);
      }
      // Redis 발급 개수를 복구
      for (CouponRequestMessage message : messages) {
        userCouponService.decrementCouponCount(message.getCouponId());
      }
    }
  }

}



//역할:Kafka에서 다운로드 요청 메시지를 수신하고 UserCouponService를 호출해 처리한다.
//장점:요청을 순서대로 처리하여 요청 순서와 발급 순서 일치를 보장한다.