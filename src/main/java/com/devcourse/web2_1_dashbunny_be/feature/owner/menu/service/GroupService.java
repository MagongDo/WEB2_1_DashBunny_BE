package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuGroupRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface GroupService {

    List<MenuGroup> read(String storeId);

    void save(String storeId, MenuGroup menuGroup);

    void update(String groupId, UpdateMenuGroupRequestDto menuGroup) ;

    void delete(String groupId);

}
