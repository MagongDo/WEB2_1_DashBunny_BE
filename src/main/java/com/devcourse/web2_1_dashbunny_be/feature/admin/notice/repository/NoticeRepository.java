package com.devcourse.web2_1_dashbunny_be.feature.admin.notice.repository;


import com.devcourse.web2_1_dashbunny_be.domain.admin.Notice;
import com.devcourse.web2_1_dashbunny_be.domain.admin.NoticeTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    //사장님과 사용자 목록 조회
    @Query("SELECT n FROM Notice n WHERE n.target = 'ENTIRE' OR n.target = :role")
    public List<Notice> findByTarget(NoticeTarget role);

    // target이 특정 Role과 일치하는 공지사항 조회
    //List<Notice> findByTarget(String target);
}
