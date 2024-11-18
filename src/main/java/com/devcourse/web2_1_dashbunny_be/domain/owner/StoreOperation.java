package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 가게의 운영정보를 관리하는 엔티티
@Getter
@Setter
@Entity
public class StoreOperation {

    @Id
    @GeneratedValue
    private Long operationId;

    @OneToOne
    @JoinColumn(name = "store_id",nullable = false, unique = true)
    private StoreManagement store;

    //영업시간 (예: "09:00-22:00")
    private String openingHours;

    //휴게시간 (예: "09:00-22:00")
    private String breakTime;

    //휴무일
    private String holidayDays;

    //휴무 안내글
    @Column(columnDefinition="TEXT")
    public String holidayNotice;

    //일시 중지 상태(불리안)
    private boolean isPaused = false;

    //일시 중지 유지 시간(시작시간 & 종료시간)
    // 고민점 영업 시간처럼 한번에 받을 것인가? 흐음,,?!? 좋습니다!! 이거에 맞게 ERD 수정했습니다!
    private String pauseStartTime;
    private String pauseEndTime;

    // 포장 주문 여부 *호정님 여기를 살렸습니다!!
    @Column(nullable = false)
    private boolean isTakeout = true;

    //사장님이 입력하는 배달 가능 지역
    private String deliveryArea;

    // 최소 배달 예상 시간 (예: 13분)
    private String minDeliveryTime;

    // 최대 배달 예상 시간 (예 : 40분)
    private String maxDeliveryTime;

    // 포장 할인 금액
    private Long takeoutDiscount;

    // 기본 배달 팁
    private Long defaultDeliveryTip;

    // 최소 주문 금액
    private Long minimumOrderPrice;
}
