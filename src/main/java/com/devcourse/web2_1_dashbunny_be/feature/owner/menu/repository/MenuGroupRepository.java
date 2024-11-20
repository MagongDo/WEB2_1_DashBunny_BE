package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {
}
