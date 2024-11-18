package com.devcourse.web2_1_dashbunny_be.domain.owner;


import com.devcourse.web2_1_dashbunny_be.annotation.config.TSID;
import com.devcourse.web2_1_dashbunny_be.domain.admin.StoreApplication;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 가게 관리 및 가게 정보를 저장하는 엔티티 클래스
@Setter
@Getter
@Entity
@Table(name = "store_management")
@EntityListeners(value={AuditingEntityListener.class})
public class StoreManagement {

    // 회원 ID
    @Id
    @TSID
    @Column(name = "store_id", nullable = false)
    private String storeId;

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
    private  String storeBannerImage;


    // 1.가게 주소 (필수, 최대 길이 255자)
    @Column(nullable = false, length = 255)
    private String address;

    // 2.가게 위치 [위도와 경도 (필수, JSON 형태로 저장)]
    @Column(columnDefinition = "JSON", nullable = false)
    private String location;


// [대표 카테고리 및 일반 카테고리 등]
    // 카테고리 1
    @Column
    private String category1;

    // 카테고리 2
    @Column
    private String category2;

    // 카테고리 3
    @Column
    private String category3;

    // 가게 등록 서류 (필수, 최대 길이 255자)
    @Column(nullable = false, length = 255)
    private String storeRegistrationDocs;

    // 평점 (소수점 1자리까지, 예: 4.5)
    @Column(nullable = true, precision = 2, scale = 1)
    private BigDecimal rating;

    // 찜한 횟수
    @Column(nullable = true)
    private int dibsCount;

    // 리뷰 수
    @Column(nullable = true)
    private int reviewCount;

    // 쇼츠 링크 (필요시 필드명 수정)
    @Column
    private String shortsUrl;

    // 최종 승인 날짜 (가장 최근 승인)
    private LocalDateTime approvedDate;

    //스토어가 가진 쿠폰 리스트
    //쿠폰이 없어도 스토어는 생성될 수 있어야한다. 리스트 초기화 진행
    @OneToMany(mappedBy = "storeManagement",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OwnerCoupon> couponList = new ArrayList<>();
    // Getters and Setters -> 롬복 사용

    @OneToMany(mappedBy = "storeManagement", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<StoreApplication> stores = new ArrayList<>();
}