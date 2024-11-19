package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.service;


import com.devcourse.web2_1_dashbunny_be.domain.admin.Notice;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.NoticeTarget;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.AdminAddNoticeRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.AdminNoticeListRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.AdminNoticeRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.dto.AdminUpdateNoticeRequestDTO;
import com.devcourse.web2_1_dashbunny_be.feature.admin.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    //공지사항 등록- 관리자만
    public Notice saveNotice(AdminAddNoticeRequestDTO request) {
        Notice savedNotice=noticeRepository.save(request.toEntity());
        return savedNotice;
    }

    //공지사항 목록 조회- 관리자
    public List<AdminNoticeListRequestDTO> getAllNotices() {
        List<Notice> notices=noticeRepository.findAll();
        return notices.stream()
                .map(AdminNoticeListRequestDTO::new)
                .toList();
    }

    //공지사항 목록 조회- 사장님,사용자
    public List<AdminNoticeListRequestDTO> getAllNoticesByRole(String role) {
        List<Notice> notices=noticeRepository.findByTarget(NoticeTarget.valueOf(role));
        return notices.stream()
                .map(AdminNoticeListRequestDTO::new)
                .toList();
    }

    //공지사항 단일 조회
    public AdminNoticeRequestDTO getNotice(Long noticeId) {
        Notice notice=noticeRepository.findById(noticeId)
                .orElseThrow(()->new IllegalArgumentException("not found notcieId: "+noticeId));
        notice.isIncrementViewCount(); //조회수 증가
        return new AdminNoticeRequestDTO(notice);
    }

    //공지사항 삭제- 관리자만
    @Transactional
    public void deleteNotice(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    //공지사항 수정- 관리자만
    @Transactional
    public AdminUpdateNoticeRequestDTO updateNotice(Long noticeId, AdminAddNoticeRequestDTO request) {
        Notice updatednotice=noticeRepository.findById(noticeId)
                .orElseThrow(()->new IllegalArgumentException("not found noticeId: "+noticeId));

        updatednotice.changeNoticeTitle(request.getNoticeTitle());
        updatednotice.changeNoticeContent(request.getNoticeContent());
        updatednotice.changeTarget(request.getTarget());

        return new AdminUpdateNoticeRequestDTO(
                updatednotice.getNoticeTitle(),
                updatednotice.getNoticeContent(),
                updatednotice.getTarget());

    }



}
