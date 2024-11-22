package com.devcourse.web2_1_dashbunny_be.domain.owner;


import com.devcourse.web2_1_dashbunny_be.annotation.config.TSID;
import com.devcourse.web2_1_dashbunny_be.annotation.config.lifecycle.TSIDListener;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// 가게 관리 및 가게 정보를 저장하는 엔티티 클래스
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store_management")
@EntityListeners(value = {TSIDListener.class})
public class StoreManagement {

    // 회원 ID
    @Id
    @TSID
    @Column(name = "store_id", nullable = false)
    private String storeId;

    // userid 빠져있음
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // operationid 빠져있음
    @OneToOne(mappedBy = "store", cascade = CascadeType.ALL)
    private StoreOperationInfo storeOperation;


    // 가게 이름 (필수, 최대 길이 255자)
    @Column(nullable = false, length = 255)
    private String storeName;

    // 가게 상태 (ENUM 타입, 기본값: PENDING)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreStatus storeStatus = StoreStatus.PENDING;

    // 가게 소개 내용 (TEXT 타입)
    @Column(columnDefinition = "TEXT")
    private String description;

    // 가게 전화번호 (필수, 최대 길이 13자)
    @Column(nullable = false, length = 13)
    private String contactNumber;

    // 가게 매장 로고 (아직 작성안한 필드)
    private String storeLogo;
    // 가게 배너 이미지 (아직 작성안한 필드)
    private String storeBannerImage;


    // 1.가게 주소 (필수, 최대 길이 255자)
    @Column(nullable = false, length = 255)
    private String address;

    // 2.가게 위치 [위도와 경도 (필수, JSON 형태로 저장)]
    @Column(columnDefinition = "JSON", nullable = false)
    private String location = "{}";

    //2. 가게 위치
    // 위도
    @Column(nullable = false)
    private Double latitude;

    //경도
    @Column(nullable = false)
    private Double longitude;

    // 가게 등록 서류 (필수, 최대 길이 255자)
    @Column(nullable = false, length = 255)
    private String storeRegistrationDocs;

    //--------------------------------------데이터 추가

    @OneToOne
    @JoinColumn(name="feedback_id")
    @ToString.Exclude
    private StoreFeedBack storeFeedback;

    @OneToOne
    @JoinColumn(name="deliveryInfo_id")
    @ToString.Exclude
    private DeliveryOperationInfo deliveryInfo;

    @OneToMany
    @JoinColumn(name="coupon_id")
    @ToString.Exclude
    private List<OwnerCoupon> ownerCoupon;

    @OneToMany
    @JoinColumn(name="flag_id")
    @ToString.Exclude
    private List<StoreFlag> storeFlags;

    @OneToMany
    @JoinColumn(name="category_id")
    @ToString.Exclude
    private List<Categorys> category;

    public BigDecimal maxDiscountPrice() {
        return ownerCoupon.stream()
                .map(OwnerCoupon::getDiscountPrice) // OwnerCoupon에서 할인 금액을 가져옴
                .max(BigDecimal::compareTo) // 가장 큰 할인 금액 찾기
                .orElse(BigDecimal.ZERO); // 값이 없을 경우 기본값으로 0 반환
    }

    //가게 등록 승인 날짜
    private LocalDateTime approvedDate;
}