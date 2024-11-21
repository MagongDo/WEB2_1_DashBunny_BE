package com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 메뉴 그룹 레포지토리 인터페이스.
 */
@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {
}
