package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// 사장님 리뷰 정보를 관리하는 엔티티 클래스
@Entity
@Table(name = "owner_review")
public class OwnerReview {

    // 사장님 리뷰 고유키 (자동 생성)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerReviewId;

    // 리뷰 ID (필수)
    @Column(nullable = false)
    private Long reviewId;

    // 리뷰 내용 (TEXT 타입, 필수)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 리뷰 생성일 (필수)
    @Column(nullable = false)
    private LocalDateTime createdDate;

    // 리뷰 수정일
    @Column
    private LocalDateTime modifiedDate;

    // 상태 (예: 활성, 비활성 등, 최대 길이 50자)
    @Column(length = 50)
    private String status;

    // 가게 ID (필수), 연관관계 매핑 아직 적용 x
    @Column(nullable = false)
    private Long storeId;

}