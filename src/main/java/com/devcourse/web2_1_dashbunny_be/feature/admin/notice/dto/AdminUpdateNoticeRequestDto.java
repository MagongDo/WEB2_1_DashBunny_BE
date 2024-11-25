package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto;


import com.devcourse.web2_1_dashbunny_be.domain.admin.role.NoticeTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 수정된 공지사항 데이터를 넘겨주는 dto.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateNoticeRequestDto {
  private String noticeTitle;
  private String noticeContent;
  private NoticeTarget target;
}
