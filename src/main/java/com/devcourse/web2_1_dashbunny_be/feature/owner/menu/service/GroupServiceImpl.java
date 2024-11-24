package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuGroupRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/** 가게 메뉴 그룹 CRUD api service.
 *
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

  private final Validator validator;
  private final MenuGroupRepository menuGroupRepository;

  @Override
  public List<MenuGroup> read(String storeId) {
    return List.of();
  }

  /**
  * 새로운 메뉴 그룹 생성을 위한 api service.
   */
  @Override
  public void save(String storeId, MenuGroup menuGroup) {
    StoreManagement store = validator.validateStoreId(storeId);
    menuGroup.setStoreId(storeId);
    menuGroupRepository.save(menuGroup);
  }

  @Override
  public void update(String groupId, UpdateMenuGroupRequestDto menuGroup) {

  }

  @Override
  public void delete(String groupId) {

  }
}
