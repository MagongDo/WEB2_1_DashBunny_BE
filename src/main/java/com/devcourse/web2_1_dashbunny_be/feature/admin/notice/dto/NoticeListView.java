package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto;

import com.devcourse.dashbunny.domain.admin.Notice;
import com.devcourse.dashbunny.domain.admin.NoticeTarget;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeListView {
    private Long noticeId;
    private String noticeTitle;
    private LocalDateTime createdDate;
    private NoticeTarget target;
    private Long viewCount;

    public NoticeListView(Notice notice) {
        this.noticeId = notice.getNoticeId();
        this.noticeTitle = notice.getNoticeTitle();
        this.createdDate = notice.getCreatedDate();
        this.target = notice.getTarget();
        this.viewCount = notice.getViewCount();
    }

}
