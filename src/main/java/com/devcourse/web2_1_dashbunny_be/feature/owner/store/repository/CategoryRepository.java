package com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository;

import com.devcourse.web2_1_dashbunny_be.domain.owner.Categorys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categorys, Long> {
  @Query("SELECT c.categoryType, COUNT(c) FROM Categorys c GROUP BY c.categoryType")
  List<Object[]> countByCategoryType();
}
