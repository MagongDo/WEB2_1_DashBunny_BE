package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuGroupRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    public List<MenuGroup> read(String storeId) {
        return null;
    }

    public void save(MenuGroup menuGroup) {
    }

    public void update(String groupId, UpdateMenuGroupRequestDTO menuGroup) {
    }

    public void delete(String groupId) {
    }

}
