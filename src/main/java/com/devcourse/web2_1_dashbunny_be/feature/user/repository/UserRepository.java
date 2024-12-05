package com.devcourse.web2_1_dashbunny_be.feature.user.repository;



import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhone(String phone);

    // userId에 해당하는 is_social을 'Y'로 업데이트하는 메서드
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isSocial = 'Y' WHERE u.userId = :userId")
    int updateIsSocialToY(@Param("userId") Long userId);

    Optional<User> findByRefreshToken(String refreshToken);

    @Modifying
    @Query("UPDATE User u SET u.refreshToken = NULL WHERE u.refreshToken = :refreshToken")
    void deleteByRefreshToken(String refreshToken);

}