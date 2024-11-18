package com.devcourse.web2_1_dashbunny_be.domain.admin;

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
    private Long noticeId;

    private String noticeTitle;

    private String noticeContent;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Enumerated(EnumType.STRING)
    private NoticeTarget target; //대상

    private Long viewCount;


    public void changeNoticeTitle(String noticeTitle) {this.noticeTitle = noticeTitle;}
    public void changeNoticeContent(String noticeContent) {this.noticeContent = noticeContent;}
    public void changeTarget(NoticeTarget target) {this.target = target;}
    public void isIncrementViewCount() {this.viewCount++;}


}
