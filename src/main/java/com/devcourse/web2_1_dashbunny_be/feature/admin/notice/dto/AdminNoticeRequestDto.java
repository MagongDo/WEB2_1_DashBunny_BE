package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto;


import com.devcourse.web2_1_dashbunny_be.domain.admin.Notice;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.NoticeTarget;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 공지사항 단일 정보 데이터를 넘겨주는 dto.
 */
@Getter
@NoArgsConstructor
public class AdminNoticeRequestDto {
  private Long noticeId;
  private String noticeTitle;
  private String noticeContent;
  private LocalDateTime createdDate;
  private NoticeTarget target;
  private Long viewCount;

  /**
   * Notice엔티티에서 DTO로 데이터를 변환을 위한 생성자.
   */
  public AdminNoticeRequestDto(Notice notice) {
    noticeId = notice.getNoticeId();
    noticeTitle = notice.getNoticeTitle();
    noticeContent = notice.getNoticeContent();
    createdDate = notice.getCreatedDate();
    target = notice.getTarget();
    viewCount = notice.getViewCount();
  }

}
