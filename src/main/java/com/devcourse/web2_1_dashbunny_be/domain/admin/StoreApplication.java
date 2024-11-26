package com.devcourse.web2_1_dashbunny_be.domain.admin;

import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreApplicationType;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.StoreIsApproved;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;



/**
 * 가게 등록 entity.
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
@Table(name = "store_application")
public class StoreApplication {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long applicationId;

  @ManyToOne
  @JoinColumn(name = "store_id")
  private StoreManagement storeManagement;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StoreApplicationType storeApplicationType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StoreIsApproved storeIsApproved = StoreIsApproved.WAIT; //승인,거절상태

  @Column(nullable = true)
  private String rejectionReason; //거절 사유

  @CreatedDate
  private LocalDateTime applicationDate; //신청 날짜

  private LocalDateTime approvedDate; //승인날짜


  /**
   * 승인처리 메서드.
   */
  public void approve() {
    this.storeIsApproved = StoreIsApproved.APPROVE;
    this.approvedDate = LocalDateTime.now();
  }


  /**
   * 거절처리 메서드.
   */
  public void reject() {
    this.storeIsApproved = StoreIsApproved.REJECT;
    this.approvedDate = LocalDateTime.now();
  }

}
