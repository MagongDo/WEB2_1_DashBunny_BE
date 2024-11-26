package com.devcourse.web2_1_dashbunny_be.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable; // 추가
import java.time.LocalDateTime;

@Entity
@Table(name = "social_users")
@Getter
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class SocialUser implements Serializable { // 변경

    private static final long serialVersionUID = 1L; // 추가

    @Id
    private String providerId;

    private String provider;

    private Long userId;

    private String userName;

    @Builder.Default
    @Column(nullable = false, length = 11)
    private String role = "ROLE_USER";

    @CreatedDate
    private LocalDateTime createdDate;

}
