package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class StoreFeedBack {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long feedbackId; // 피드백 고유 ID

  @Column(name = "store_id", nullable = false, unique = true)
  private String storeId;

  @Column(nullable = true)
  private Double reviewCount; // 리뷰 수

  @Column(nullable = true)
  private Double totalRating;

  @Column(nullable = true)
  private Double rating; // 평점

  @Column(nullable = true)
  private Integer wishlistCount = 0; // 찜 수

  public Double increaseReviewCount() {
  return this.reviewCount + 1;
  }

  public Integer increaseWishCount() {
  return this.wishlistCount + 1;
  }

  public Integer decreaseWishCount() {
  return this.wishlistCount - 1;
  }
}
