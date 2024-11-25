package com.devcourse.web2_1_dashbunny_be.domain.admin;

import com.devcourse.web2_1_dashbunny_be.domain.admin.role.NoticeTarget;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value={AuditingEntityListener.class})
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long noticeId;

    @Column(nullable = false)
    private String noticeTitle;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String noticeContent;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeTarget target=NoticeTarget.ENTIRE; //대상 (디폴트:전체)간접참조

    @Column(nullable =false)
    private Long viewCount;

    public void changeNoticeTitle(String noticeTitle) {this.noticeTitle = noticeTitle;}
    public void changeNoticeContent(String noticeContent) {this.noticeContent = noticeContent;}
    public void changeTarget(NoticeTarget target) {this.target = target;}
    public void isIncrementViewCount() {this.viewCount++;}

}
