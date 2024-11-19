package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto;


import com.devcourse.web2_1_dashbunny_be.domain.admin.Notice;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.NoticeTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddNotice {
    private String noticeTitle;
    private String noticeContent;
    private NoticeTarget target;

    public Notice toEntity(){
        return Notice.builder()
                .noticeTitle(noticeTitle)
                .noticeContent(noticeContent)
                .target(target)
                .viewCount(0L)
                .build();
    }
}
