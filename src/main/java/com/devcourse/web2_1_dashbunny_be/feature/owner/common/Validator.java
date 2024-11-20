package com.devcourse.web2_1_dashbunny_be.feature.owner.common;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Validator {

    private final MenuGroupRepository menuGroupRepository;

    //메뉴 그룹 아이디 검증 메서드
    public MenuGroup validateGroupId(Long groupId) {
        return menuGroupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("해당 엔티티를 찾을 수 없습니다."));
    }

}
