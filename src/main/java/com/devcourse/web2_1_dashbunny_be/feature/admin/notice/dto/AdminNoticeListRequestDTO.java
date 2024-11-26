package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto;


import com.devcourse.web2_1_dashbunny_be.domain.admin.Notice;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.NoticeTarget;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminNoticeListRequestDTO {
    private Long noticeId;
    private String noticeTitle;
    private LocalDateTime createdDate;
    private NoticeTarget target;
    private Long viewCount;

    public AdminNoticeListRequestDTO(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.noticeTitle = notice.getNoticeTitle();
        this.createdDate = notice.getCreatedDate();
        this.target = notice.getTarget();
        this.viewCount = notice.getViewCount();
    }

}

//JPA가 강제하는 기본 생성자 규칙은 DTO에 적용되지 않는다.->@NoArgsConstructor가 꼭 필요한것은 아님