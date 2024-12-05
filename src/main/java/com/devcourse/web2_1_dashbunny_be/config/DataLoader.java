//package com.devcourse.web2_1_dashbunny_be.config;
//
//import com.devcourse.web2_1_dashbunny_be.domain.owner.*;
//import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CategoryType;
//import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
//import com.devcourse.web2_1_dashbunny_be.domain.user.User;
//import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
//import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
//import com.devcourse.web2_1_dashbunny_be.feature.owner.store.repository.*;
//import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class DataLoader implements CommandLineRunner {
//
//    private final StoreManagementRepository storeRepository;
//    private final MenuGroupRepository menuGroupRepository;
//    private final MenuRepository menuRepository;
//    private final CategoryRepository categoryRepository;
//    private final DeliveryOperatingInfoRepository deliveryInfoRepository;
//    private final UserRepository userRepository;
//    private final StoreFeedBackRepository storeFeedBackRepository; // Injected StoreFeedBackRepository
//    private final StoreFlagRepository storeFlagRepository; // Injected StoreFlagRepository
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        List<Categorys> categories = new ArrayList<>();
//        List<MenuGroup> menuGroups = new ArrayList<>();
//        List<MenuManagement> menus = new ArrayList<>();
//        List<StoreFlag> storeFlags = new ArrayList<>(); // StoreFlag 리스트 추가
//
//        // Fetch the user once outside the loop to avoid multiple database hits
//        User user = userRepository.findById(1L)
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + 1L));
//
//        for (int i = 1; i <= 300; i++) {
//            // Store 생성
//            StoreManagement store = new StoreManagement();
//            store.setStoreName("Store " + i);
//            store.setStoreStatus(StoreStatus.OPEN);
//            store.setStoreDescription("Description for Store " + i);
//            store.setContactNumber("010-1234-" + String.format("%04d", i));
//            store.setAddress("Address for Store " + i);
//            store.setLatitude(37.56 + (i * 0.0001)); // 위도 조정
//            store.setLongitude(126.97 + (i * 0.0001)); // 경도 조정
//            store.setStoreRegistrationDocs("https://example.com/docs/store" + i);
//            store.setStoreLogo("https://example.com/logo/store" + i + ".png");
//            store.setStoreBannerImage("https://example.com/banner/store" + i + ".png");
//
//            store.setUser(user);
//
//            // Save StoreManagement first to generate ID
//            storeRepository.save(store);
//
//            // StoreFeedBack 생성 및 연관
//            StoreFeedBack feedback = new StoreFeedBack();
//            feedback.setRating(3.5 + (i % 5)); // 3.5~4.5
//            feedback.setReviewCount(10 * i);
//            feedback.setWishlistCount(5 * i);
//            feedback.setStoreId(store.getStoreId());
//
//            // Save StoreFeedBack via its repository
//            storeFeedBackRepository.save(feedback);
//
//
//
//            // DeliveryOperationInfo 생성
//            DeliveryOperatingInfo deliveryInfo = new DeliveryOperatingInfo();
//            deliveryInfo.setMinDeliveryTime((20 + (i % 10)) + "분");
//            deliveryInfo.setMaxDeliveryTime((30 + (i % 20)) + "분");
//            deliveryInfo.setDeliveryTip(2000L + (i * 10));
//            deliveryInfo.setMinOrderAmount(10000L + (i * 100));
//            deliveryInfo.setDeliveryAreaInfo("Delivery area for Store " + i);
//            deliveryInfo.setStoreId(store.getStoreId());
//
//            // Save DeliveryOperationInfo
//            deliveryInfoRepository.save(deliveryInfo);
//
//
//            // Category 추가 및 연관
//            Categorys category1 = new Categorys();
//            category1.setCategoryType(CategoryType.CHICKEN);
//            category1.setStoreManagement(store);
//
//            Categorys category2 = new Categorys();
//            category2.setCategoryType(CategoryType.KOREAN);
//            category2.setStoreManagement(store);
//
//            categories.add(category1);
//            categories.add(category2);
//            store.setCategory(List.of(category1, category2));
//
//            // MenuGroup 및 Menu 추가
//            MenuGroup menuGroup = new MenuGroup();
//            menuGroup.setGroupName("Menu Group for Store " + i);
//            menuGroup.setStoreId(store.getStoreId());
//
//            List<MenuManagement> menuList = new ArrayList<>();
//            for (int j = 1; j <= 5; j++) { // 각 가게에 5개의 메뉴 추가
//                MenuManagement menu = new MenuManagement();
//                menu.setMenuName("Menu " + j + " for Store " + i);
//                menu.setPrice(10000L + (j * 500));
//                menu.setMenuContent("Description for Menu " + j + " in Store " + i);
//                menu.setMenuImage("https://example.com/menu/store" + i + "/menu" + j + ".png");
//                menu.setStockAvailable(true);
//                menu.setMenuStock(50 + j * 10);
//                menu.setMenuGroup(menuGroup);
//                menu.setStoreId(store.getStoreId());
//                menuList.add(menu);
//            }
//            menuGroup.setMenuList(menuList);
//            menus.addAll(menuList);
//
//            menuGroups.add(menuGroup);
//
//            // StoreFlag 추가: 각 가게마다 3개의 플래그 생성
//            for (int k = 1; k <= 3; k++) {
//                StoreFlag storeFlag = new StoreFlag();
//                storeFlag.setStoreManagement(store);
//                // 위도와 경도에 약간의 변동을 주어 비슷한 위치에 플래그 설정
//                storeFlag.setLatitude(store.getLatitude() + (k * 0.0001));
//                storeFlag.setLongitude(store.getLongitude() + (k * 0.0001));
//                storeFlags.add(storeFlag);
//            }
//        }
//
//        // After the loop, save all categories, menu groups, menus, and store flags
//        categoryRepository.saveAll(categories); // Category 저장
//        menuGroupRepository.saveAll(menuGroups); // MenuGroup 저장
//        menuRepository.saveAll(menus); // Menu 저장
//        storeFlagRepository.saveAll(storeFlags); // StoreFlag 저장
//
//        System.out.println("100 stores and related data have been inserted.");
//    }
//}