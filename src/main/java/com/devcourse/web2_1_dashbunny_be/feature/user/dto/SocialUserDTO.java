package com.devcourse.web2_1_dashbunny_be.feature.user.dto;

import com.devcourse.web2_1_dashbunny_be.domain.user.SocialUser;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserDTO {

    private String providerId;

    private String provider;

    private Long userId;

    private String userName;

    public SocialUserDTO(SocialUser socialUser) {
        this.providerId = socialUser.getProviderId();
        this.provider = socialUser.getProvider();
        this.userId = socialUser.getUserId();
        this.userName = socialUser.getUserName();

    }

    public SocialUser toEntity() {
        return SocialUser.builder()
                .providerId(providerId)
                .provider(provider)
                .userId(userId)
                .userName(userName)
                .build();
    }

}
