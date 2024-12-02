package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.Categorys;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categorys, Long> {
}
