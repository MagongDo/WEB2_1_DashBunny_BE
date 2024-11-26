package com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.service;

import com.devcourse.web2_1_dashbunny_be.domain.admin.AdminCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponStatus;
import com.devcourse.web2_1_dashbunny_be.domain.admin.role.CouponType;
import com.devcourse.web2_1_dashbunny_be.domain.owner.OwnerCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.User;
import com.devcourse.web2_1_dashbunny_be.domain.user.UserCoupon;
import com.devcourse.web2_1_dashbunny_be.domain.user.role.IssuedCouponType;
import com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon.repository.AdminCouponRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.ownerCoupon.repository.OwnerCouponRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.dto.GeneralCouponListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.userCoupon.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCouponService {
    private final UserCouponRepository userCouponRepository;
    private final AdminCouponRepository adminCouponRepository;
    private final UserRepository userRepository;
    private final OwnerCouponRepository ownerCouponRepository;

    /**
     * 관리자가 발급한 활성화된 일반 쿠폰을 조회하는 메서드.
     */
    public List<GeneralCouponListResponseDto> findActiveRegularCoupons() {
        List<AdminCoupon> adminCoupons = adminCouponRepository.findByCouponTypeAndCouponStatus(
                CouponType.Regula,
                CouponStatus.ACTIVE);
        return adminCoupons.stream()
                .map(GeneralCouponListResponseDto::new)
                .toList();
    }

    /**
     * 관리자가 발급한 활성화된  쿠폰을 다운로드하는 메서드.
     */
    public UserCoupon downloadCoupon(Long couponId, IssuedCouponType issuedCouponType) {

        // 현재 로그인된 사용자 ID 가져오기
        Long userId = SecurityUtil.getCurrentUserId();

        // 2. 사용자 정보 조회
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 없습니다. id: " + userId));

        // 3. 이미 다운로드한 쿠폰인지 확인
        boolean alreadyDownloaded = userCouponRepository.existsByUser_IdAndCouponIdAndIssuedCouponType(userId, couponId, issuedCouponType);

        if (alreadyDownloaded) {
            throw new IllegalArgumentException("이미 이 쿠폰을 다운로드 받았습니다.");
        }

        // 4. 발급 유형별 다운로드 로직 처리
        switch (issuedCouponType) {
            case ADMIN: //관리자가 발급한 쿠폰
                return downloadAdminCoupon(userId, couponId);
            case OWNER: //사장님이 발급한 쿠폰
                return downloadOwnerCoupon(userId, couponId);
//            case FIRST_COME:
//                return downloadFirstComeCoupon(userId, couponId);
            default:
                throw new IllegalArgumentException("잘못된 쿠폰 유형입니다.");
        }
    }


    /**
     * 관리자가 발급한 쿠폰 다운로드 메소드.
     */
    private UserCoupon downloadAdminCoupon(User currentUser, Long couponId) {
        //Admin 쿠폰 조회
        AdminCoupon adminCoupon = adminCouponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다."));

        //선착순 쿠폰이면 분리된 메소드 이용(레디스 사용)
        if(adminCoupon.getCouponType() == CouponType.FirstCome){
            return downloadFirstComeCoupon(currentUser, couponId);
        }

        //UserCoupon 객체 생성
        UserCoupon downloadCoupon=UserCoupon.builder()
                .user(currentUser)
                .couponId(couponId)
                .issuedCouponType(IssuedCouponType.ADMIN)
                .usedDate(null)
                .couponUsed(false)
                .build();

        return userCouponRepository.save(downloadCoupon); //쿠폰 저장
    }

    private UserCoupon downloadOwnerCoupon(User currentUser, Long couponId) {
        //Owner 쿠폰 조회
        OwnerCoupon ownerCoupon = ownerCouponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다."));

        //UserCoupon 객체 생성
        UserCoupon downloadCoupon = UserCoupon.builder()
                .user(currentUser)
                .couponId(couponId)
                .issuedCouponType(IssuedCouponType.OWNER)
                .usedDate(null)
                .couponUsed(false)
                .build();

        return userCouponRepository.save(downloadCoupon); //쿠폰 저장

    }

    private UserCoupon downloadFirstComeCoupon(User currentUser, Long couponId) {

        //레디스 사용
    }



}
