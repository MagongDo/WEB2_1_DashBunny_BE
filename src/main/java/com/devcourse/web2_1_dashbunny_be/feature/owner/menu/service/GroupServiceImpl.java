package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.common.Validator;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuGroupRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/** 가게 메뉴 그룹 CRUD api service.
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

  private final Validator validator;
  private final MenuGroupRepository menuGroupRepository;

  @Override
  public List<MenuGroup> read(String storeId) {
    StoreManagement store = validator.validateStoreId(storeId);
    return menuGroupRepository.findByStoreId(storeId);
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

  /**
   * 메뉴 그룹 수정을 위한 api service.
   */
  @Override
  public void update(Long groupId, UpdateMenuGroupRequestDto menuGroup) {
    MenuGroup group = validator.validateGroupId(groupId);

    if(menuGroup.getGroupName() != null) {
      group.setGroupName(menuGroup.getGroupName());
    }

    log.info("대표메뉴 상태 {}", menuGroup.isMainGroup());
    log.info("기존 메뉴 상태 {}", group.getIsMainGroup());

    if(menuGroup.isMainGroup() != group.getIsMainGroup()){
      group.setIsMainGroup(menuGroup.isMainGroup());
    }

    menuGroupRepository.save(group);
  }

  @Override
  public void delete(String groupId) {

  }
}
