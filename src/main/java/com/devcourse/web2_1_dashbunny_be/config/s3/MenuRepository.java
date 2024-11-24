package com.devcourse.web2_1_dashbunny_be.config.s3;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuManagement, Long> {
}
