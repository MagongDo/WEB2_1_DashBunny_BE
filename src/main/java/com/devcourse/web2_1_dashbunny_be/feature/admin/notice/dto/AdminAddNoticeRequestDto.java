package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto;


import com.devcourse.web2_1_dashbunny_be.domain.admin.Notice;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.NoticeTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자 공지사항 생성 정보를 받는 dto.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddNoticeRequestDto {
  private String noticeTitle;
  private String noticeContent;
  private NoticeTarget target;

  /**
   * Notice 객체 생성 메소드.
   */
  public Notice toEntity() {
    return Notice.builder()
            .noticeTitle(noticeTitle)
            .noticeContent(noticeContent)
            .target(target)
            .viewCount(0L)
            .build();
  }
}
