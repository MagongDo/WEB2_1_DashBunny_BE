package com.devcourse.web2_1_dashbunny_be.config.s3;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;

    /**
     * 메뉴 url 저장 로직
     * @param menuId
     * @param fileUrl
     */
  public void updateMenuImage(Long menuId, String fileUrl) {
    MenuManagement menu = menuRepository.findById(menuId)
            .orElseThrow(() -> new RuntimeException("해당 메뉴를 찾을 수 없습니다."));
    menu.setMenuImage(fileUrl);
    menuRepository.save(menu);
    }
}
