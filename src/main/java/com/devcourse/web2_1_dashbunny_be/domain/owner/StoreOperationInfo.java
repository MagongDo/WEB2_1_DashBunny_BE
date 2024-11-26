package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.*;
import lombok.*;

// 가게의 운영정보를 관리하는 엔티티
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class StoreOperationInfo {

    @Id
    @GeneratedValue
    private Long operationId;

    @OneToOne
    @JoinColumn(name = "store_id",nullable = false, unique = true)
    private StoreManagement store;

        // 쇼츠 링크 (필요시 필드명 수정)
    @Column
    private String shortsUrl;

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


}
