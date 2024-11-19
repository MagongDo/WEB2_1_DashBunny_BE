package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto;


import com.devcourse.web2_1_dashbunny_be.domain.admin.Notice;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.NoticeTarget;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NoticeView {
    private Long noticeId;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime createdDate;
    private NoticeTarget target;
    private Long viewCount;

    public NoticeView(Notice notice) {
        noticeId=notice.getNoticeId();
        noticeTitle=notice.getNoticeTitle();
        noticeContent=notice.getNoticeContent();
        createdDate=notice.getCreatedDate();
        target=notice.getTarget();
        viewCount=notice.getViewCount();
    }

}
