package com.devcourse.web2_1_dashbunny_be.feature.user.repository;



import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.domain.delivery.role.DeliveryWorkerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.List;
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

    @Query("SELECT u.role, COUNT(u) FROM User u GROUP BY u.role")
    List<Object[]> countByUserOwnerCount();
    /**
     * deliveryStatus가 READY이고, role이 ROLE_DELIVERY이며,
     * latitude와 longitude가 지정된 값과 일치하는 모든 유저를 조회합니다.
     *
     * @param deliveryStatus 배달 상태
     * @param role 유저의 역할
     * @param latitude 위도
     * @param longitude 경도
     * @return 조건에 맞는 유저 목록
     */
    List<User> findByDeliveryStatusAndRoleAndLatitudeAndLongitude(
            DeliveryWorkerStatus deliveryStatus,
            String role,
            Double latitude,
            Double longitude
    );

    /**
     * 지정된 위치 기준으로 반경 내에 있으며,
     * 배달 상태가 READY이고 역할이 ROLE_DELIVERY인 사용자들을 조회.
     *
     * @param latitude 기준 지점의 위도
     * @param longitude 기준 지점의 경도
     * @param radius 반경 (킬로미터 단위)
     * @return 반경 내에 있는 사용자 목록
     */
    @Query(value = "SELECT *, " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * " +
            "cos(radians(longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(latitude)))) AS distance " +
            "FROM users " +
            "WHERE delivery_status = 'READY' AND role = 'ROLE_DELIVERY' " +
            "HAVING distance < :radius " +
            "ORDER BY distance", nativeQuery = true)
    List<User> findAvailableDeliveryUsersWithinRadius(
            @Param("lat") double latitude,
            @Param("lon") double longitude,
            @Param("radius") double radius
    );

}