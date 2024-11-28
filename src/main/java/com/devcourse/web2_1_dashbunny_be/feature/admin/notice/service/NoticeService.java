package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.service;


import com.devcourse.web2_1_dashbunny_be.domain.admin.Notice;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.NoticeTarget;
import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.AdminAddNoticeRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.AdminNoticeListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.AdminNoticeResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.AdminUpdateNoticeRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.repository.NoticeRepository;
import java.util.List;
import java.util.Optional;

import com.devcourse.web2_1_dashbunny_be.feature.user.repository.SocialUserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 공지사항 serice.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class NoticeService {
  private final NoticeRepository noticeRepository;
  private final SocialUserRepository socialUserRepository;
  private final UserRepository userRepository;


  /**
   *공지사항 등록- 관리자만.
   */
  public Notice saveNotice(AdminAddNoticeRequestDto request) {
    return noticeRepository.save(request.toEntity());
  }


  /**
   *공지사항 목록 조회- 관리자.
   */
  public List<AdminNoticeListResponseDto> getAllNotices() {
    List<Notice> notices = noticeRepository.findAll();
    return notices.stream()
          .map(AdminNoticeListResponseDto::new)
          .toList();
  }


  /**
   *공지사항 목록 조회- 사장님,사용자.
   */
  public List<AdminNoticeListResponseDto> getAllNoticesByRole(String role) {
    List<Notice> notices;
    if (role.equals("ROLE_USER")) {
      notices = noticeRepository.findByTarget(NoticeTarget.USER);
    } else {
      notices = noticeRepository.findByTarget(NoticeTarget.OWNER);
    }

    return notices.stream()
            .map(AdminNoticeListResponseDto::new)
            .toList();
  }


  /**
   * 공지사항 단일 조회.
   */
  @Transactional
  public AdminNoticeResponseDto getNotice(Long noticeId) {
    Notice notice = noticeRepository.findById(noticeId)
            .orElseThrow(() -> new IllegalArgumentException("not found noticeId: " + noticeId));
    notice.isIncrementViewCount(); //조회수 증가
    return new AdminNoticeResponseDto(notice);
  }



  /**
   *공지사항 삭제- 관리자만.
   */
  @Transactional
  public void deleteNotice(Long noticeId) {
    noticeRepository.deleteById(noticeId);
  }


  /**
   *공지사항 수정- 관리자만.
   */
  @Transactional
  public AdminUpdateNoticeRequestDto updateNotice(Long noticeId, AdminAddNoticeRequestDto request) {
    Notice updatednotice = noticeRepository.findById(noticeId)
            .orElseThrow(() -> new IllegalArgumentException("not found noticeId: " + noticeId));

    updatednotice.changeNoticeTitle(request.getNoticeTitle());
    updatednotice.changeNoticeContent(request.getNoticeContent());
    updatednotice.changeTarget(request.getTarget());

    return new AdminUpdateNoticeRequestDto(
              updatednotice.getNoticeTitle(),
              updatednotice.getNoticeContent(),
              updatednotice.getTarget());

  }

//  /**
//   * 소셜 로그인 사용자인지 확인후 role 반환 메소드.
//   */
//  public String socialUserRole(String providerId) {
//    if(socialUserRepository.findByProviderId(providerId).isPresent()) {
//      return socialUserRepository.findByProviderId(providerId).get().getRole();
//    }else {
//      return null;
//    }
//  }
//
//  /**
//   * 일반 로그인 사용자인지 확인.
//   */
//  public String generalUserRole(String phone) {
//    if(userRepository.findByPhone(phone).isPresent()) {
//      return userRepository.findByPhone(phone).get().getRole();
//    } else {
//      return null;
//    }
//  }

  /**
   * 소셜 로그인 사용자인지, 일반 로그인 사용자인지 확인 후 role반환 메소드.
   * @param currentUser
   * @return
   */
  public String getCurrentUserRole(String currentUser) {
    // 소셜 사용자 권한 조회
    Optional<String> socialUserRole = socialUserRepository.findByProviderId(currentUser)
            .map(SocialUser::getRole);

    if (socialUserRole.isPresent()) {
      return socialUserRole.get();
    }

    // 일반 사용자 권한 조회
    return userRepository.findByPhone(currentUser)
            .map(User::getRole)
            .orElse(null);
  }





}
