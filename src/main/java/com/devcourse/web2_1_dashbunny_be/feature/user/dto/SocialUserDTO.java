package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserDTO {

  private String providerId;

  private String provider;

  private String userName;

  public SocialUserDTO(SocialUser socialUser) {
    this.providerId = socialUser.getProviderId();
    this.provider = socialUser.getProvider();
    this.userName = socialUser.getUserName();

  }

  public SocialUser toEntity() {
    return SocialUser.builder()
      .providerId(providerId)
      .provider(provider)
      .userName(userName)
      .build();
  }

}
