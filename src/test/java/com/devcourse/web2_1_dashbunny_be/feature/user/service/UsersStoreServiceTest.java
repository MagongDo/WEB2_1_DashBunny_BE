/*
package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import com.devcourse.web2_1_dashbunny_be.config.KakaoGeocoding;
import com.devcourse.web2_1_dashbunny_be.config.RedisKeyUtil;
import com.devcourse.web2_1_dashbunny_be.domain.owner.*;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.CategoryType;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.repository.StoreManagementRepository;
import com.devcourse.web2_1_dashbunny_be.feature.user.dto.UsersStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.user.repository.UserRepository;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UsersStoreServiceTest {

    @Mock
    private StoreManagementRepository storeManagementRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private UsersStoreService usersStoreService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

//        usersStoreService = new UsersStoreService(storeManagementRepository, userRepository);
        ReflectionTestUtils.setField(usersStoreService, "redisTemplate", redisTemplate);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testUsersStoreListResponse() {
        String userId = "testUser";
        String address = "Seoul, South Korea";
        String category = "KOREAN";

        // Mock 좌표 변환
        JsonObject mockCoordinates = MockGeoUtils.getMockCoordinates();
        try (var mockedStatic = mockStatic(KakaoGeocoding.class)) {
//            mockedStatic.when(() -> KakaoGeocoding.getCoordinatesFromAddress(address)).thenReturn(mockCoordinates);

            // Redis Mock 설정
            String redisKey = RedisKeyUtil.generateKey(userId, mockCoordinates.get("latitude").getAsDouble(), mockCoordinates.get("longitude").getAsDouble());
            List<String> storeIds = List.of("store1", "store2", "store3");
            when(redisTemplate.hasKey(redisKey)).thenReturn(true);
            when(valueOperations.get(redisKey)).thenReturn(storeIds);

            // Mock Store 데이터
            StoreManagement mockStore = mock(StoreManagement.class);
            when(mockStore.getStoreId()).thenReturn("store1");
            when(mockStore.getStoreName()).thenReturn("KOREAN Restaurant");
//            when(mockStore.getCategory()).thenReturn(List.of(new Categorys(CategoryType.KOREAN)));

            // Mock StoreFeedback
            StoreFeedBack mockFeedback = mock(StoreFeedBack.class);
            when(mockFeedback.getRating()).thenReturn(4.5);
            when(mockFeedback.getReviewCount()).thenReturn(100);
            when(mockStore.getStoreFeedback()).thenReturn(mockFeedback);

            // Mock DeliveryInfo
            DeliveryOperationInfo mockDeliveryInfo = mock(DeliveryOperationInfo.class);
            when(mockDeliveryInfo.getBaseDeliveryTip()).thenReturn(3000L);
            when(mockDeliveryInfo.getMinDeliveryTime()).thenReturn("20분");
            when(mockDeliveryInfo.getMaxDeliveryTime()).thenReturn("40분");
            when(mockStore.getDeliveryInfo()).thenReturn(mockDeliveryInfo);

            when(storeManagementRepository.findById("store1")).thenReturn(Optional.of(mockStore));

            // 테스트 메서드 실행
            List<UsersStoreListResponseDto> result = usersStoreService.usersStoreListResponse(userId, address, category);

            System.out.println("결과 리스트 크기: " + result.size());
            for (UsersStoreListResponseDto dto : result) {
                System.out.println("가게 이름: " + dto.getStoreName());
                System.out.println("평점: " + dto.getRating());
                System.out.println("리뷰 수: " + dto.getReviewCount());
                System.out.println("배달 팁: " + dto.getBaseDeliveryTip());
                System.out.println("최소 배달 시간: " + dto.getMinDeliveryTime());
                System.out.println("최대 배달 시간: " + dto.getMaxDeliveryTime());
            }

            // 검증
            assertEquals(1, result.size());
            assertEquals("KOREAN Restaurant", result.get(0).getStoreName());
            assertEquals(4.5, result.get(0).getRating());
            assertEquals(100, result.get(0).getReviewCount());
        }
    }
}
*/
