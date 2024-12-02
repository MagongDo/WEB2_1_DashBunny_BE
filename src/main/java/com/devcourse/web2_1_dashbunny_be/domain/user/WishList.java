package com.devcourse.web2_1_dashbunny_be.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishList {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long wishListId;
  private String storeId;
  private Long userId;


}
