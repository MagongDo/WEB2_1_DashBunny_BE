//package com.devcourse.web2_1_dashbunny_be.domain.owner;
//
//import com.devcourse.dashbunny.domain.owner.role.CouponStatus;
//import com.devcourse.dashbunny.domain.owner.role.DiscountType;
//import com.devcourse.dashbunny.domain.owner.role.StoreStatus;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@Transactional
//class StoreManagementTest {
//    // 테스트 할 것 가게와 쿠폰의 연관 관계
//    // 가게는 쿠폰이 없어도 생성/존재가 가능하다.
//    // 쿠폰은 가게 없이는 생성이 불가능하다.
//    // 한 개의 가게에는 여러개의 쿠폰을 가질 수 있다.
//    // 쿠폰은 한개의 가게 객체만 참조할 수 있다.
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    // 가게와 쿠폰이 manyToOne OneToMany관계로 데이터베이스에 잘 저장이 되는가
//    @Test
//    public void testH2Save(){
//        //given
//        //가게
//        StoreManagement store = new StoreManagement();
//        store.setStoreId("store-001");
//        store.setStoreName("피자가게");
//        store.setAddress("서울특별시 강남구 테헤란로 123");
//        store.setContactNumber("010-1234-5678");
//        store.setLocation("dnleh");
//  /*      store.setType(StoreStatus.PENDING.ordinal());*/
//        store.setStoreRegistrationDocs("sdsd");
//        entityManager.persist(store);
//
//        OwnerCoupon coupon1 = new OwnerCoupon();
//        coupon1.setStoreManagement(store);
//        coupon1.setCouponStatus(CouponStatus.ONGOING);
//        coupon1.setCouponName("10% Discount");
//        coupon1.setDiscountPrice(BigDecimal.valueOf(5000.00));
//        coupon1.setMinDeliveryPrice(BigDecimal.valueOf(20000.00));
//        coupon1.setExpiryDate(LocalDate.now().plusDays(30));
//        coupon1.setDiscountType(DiscountType.PERCENT);
//        coupon1.setMaximumDiscount(BigInteger.valueOf(10000));
//
//        entityManager.persist(coupon1);
//
//        OwnerCoupon coupon2 = new OwnerCoupon();
//        coupon2.setStoreManagement(store);
//        coupon2.setCouponStatus(CouponStatus.ONGOING);
//        coupon2.setCouponName("10% Discount");
//        coupon2.setDiscountPrice(BigDecimal.valueOf(5000.00));
//        coupon2.setMinDeliveryPrice(BigDecimal.valueOf(20000.00));
//        coupon2.setExpiryDate(LocalDate.now().plusDays(30));
//        coupon2.setDiscountType(DiscountType.PERCENT);
//        coupon2.setMaximumDiscount(BigInteger.valueOf(10000));
//
//        entityManager.persist(coupon2);
//
//        store.getCouponList().add(coupon1);
//        store.getCouponList().add(coupon2);
//
//        entityManager.persist(store);
//        entityManager.flush();
//        entityManager.clear();
//
//        StoreManagement foundStore = entityManager.find(StoreManagement.class, store.getStoreId());
//        //가게가 잘 생성이되었는가?
//        assertNotNull(foundStore);
//        //한개의 가게 리스트에 두개의 쿠폰이 모두 잘 담겼는가?
//        assertEquals(2, foundStore.getCouponList().size());
//        // 저장된 쿠폰의 가게 아이디가 동일 한가?
//        assertEquals(foundStore.getCouponList().get(0).getStoreManagement().getStoreId(), foundStore.getCouponList().get(1).getStoreManagement().getStoreId());
//    }
//
//    //가게가 삭제 될 때 쿠폰이 모두 삭제가 되는가
//    @Test
//    public void testDeleteStore(){
//        StoreManagement store = new StoreManagement();
//        store.setStoreId("store-001");
//        store.setStoreName("피자가게");
//        store.setAddress("서울특별시 강남구 테헤란로 123");
//        store.setContactNumber("010-1234-5678");
//        store.setLocation("dnleh");
///*        store.setType(StoreStatus.PENDING.ordinal());*/
//        store.setStoreRegistrationDocs("sdsd");
//        entityManager.persist(store);
//
//        OwnerCoupon coupon = new OwnerCoupon();
//        coupon.setStoreManagement(store);
//        coupon.setCouponStatus(CouponStatus.ONGOING);
//        coupon.setCouponName("10% Discount");
//        coupon.setDiscountPrice(BigDecimal.valueOf(5000.00));
//        coupon.setMinDeliveryPrice(BigDecimal.valueOf(20000.00));
//        coupon.setExpiryDate(LocalDate.now().plusDays(30));
//        coupon.setDiscountType(DiscountType.PERCENT);
//        coupon.setMaximumDiscount(BigInteger.valueOf(10000));
//
//        store.getCouponList().add(coupon);
//
//        entityManager.persist(store);
//        entityManager.flush();
//        entityManager.clear();
//
//        StoreManagement foundStore = entityManager.find(StoreManagement.class, store.getStoreId());
//        assertNotNull(foundStore);
//        entityManager.remove(foundStore);
//        assertNull(entityManager.find(OwnerCoupon.class, coupon.getCouponId()));
//    }
//
//}