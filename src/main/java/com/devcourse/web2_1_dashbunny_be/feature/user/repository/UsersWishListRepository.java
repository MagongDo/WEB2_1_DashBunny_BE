package com.devcourse.web2_1_dashbunny_be.feature.user.repository;

import com.devcourse.web2_1_dashbunny_be.domain.user.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersWishListRepository extends JpaRepository<WishList, Long> {
  WishList findByStoreIdAndUserId(String storeId, Long userId);
  List<WishList> findByUserId(Long userId);

  /*
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT s FROM StoreManagement s WHERE s.storeId = :storeId")
  StoreManagement findByStoreIdWithLock(@Param("storeId") String storeId);
  */

}
