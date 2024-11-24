package com.devcourse.web2_1_dashbunny_be.feature.admin.store.controller;

import com.devcourse.web2_1_dashbunny_be.config.SecurityConfig;
import com.devcourse.web2_1_dashbunny_be.domain.owner.StoreManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.role.StoreStatus;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.AdminStoreResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.dto.StoreCreateRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreApplicationService;
import com.devcourse.web2_1_dashbunny_be.feature.admin.store.service.StoreManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StoreRequestController.class)
@ActiveProfiles("test")
@Import(SecurityConfig.class)
class StoreRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StoreManagementService storeManagementService;

    @MockBean
    private StoreApplicationService storeApplicationService;

    // JPA Auditing 관련 Mock 처리
    @MockBean
    private AuditingHandler auditingHandler;

    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void createStore()throws Exception {
        // GIVEN
        StoreCreateRequestDto requestDto = new StoreCreateRequestDto(
                "Store A",
                "010-1234-5678",
                "서울시영등포구 도신로 29길 28",
                "닭갈비 맛 끝내줍니다! 어서오세요 단체주문 환영",
                "한식",
                "한식",
                "한식",
                ".....pdf",
                "..png",
                "mmm.img",
                StoreStatus.PENDING);

        // Mock 처리: JPA 관련 동작을 포함한 실제 서비스 호출 차단
        //doNothing().when(storeManagementService).create(any(StoreCreateRequestDto.class));


        // WHEN & THEN
        mockMvc.perform(post("/api/store/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("가게 등록 승인 요청을 성공했습니다."));

        verify(storeManagementService, times(1)).create(any(StoreCreateRequestDto.class));
    }


    @Test
    void recreateStore() {
    }

    @Test
    void closeStore()  throws Exception {
        // GIVEN
        String storeId = "123";

        StoreManagement mockStoreManagement = new StoreManagement(); // 테스트용 Mock 객체 생성
        mockStoreManagement.setStoreId(storeId); // 필요한 속성 설정
        mockStoreManagement.setStoreStatus(StoreStatus.CLOSURE_PENDING);


        // Mocking: storeManagementService.close() 호출 시 반환값 설정
        when(storeManagementService.close(storeId)).thenReturn(mockStoreManagement);

        // WHEN & THEN
        mockMvc.perform(post("/api/store/closure/" + storeId))
                .andExpect(status().isOk())
                .andExpect(content().string("가게 폐업 승인 요청을 성공했습니다."));

        // 서비스 메서드가 호출되었는지 확인
        verify(storeManagementService, times(1)).close(storeId);
    }

    @Test
    void approveCreatedStore()throws Exception  {
        // GIVEN
        String storeId = "123";

        // Mocking: storeApplicationService.approve() 호출시 아무 작업 없이 반환
        doNothing().when(storeApplicationService).approve(storeId);

        // WHEN & THEN
        mockMvc.perform(put("/api/store/approve/" + storeId))
                .andExpect(status().isOk())
                .andExpect(content().string("가게 등록을 승인했습니다."));

        // 서비스 메서드가 호출되었는지 확인
        verify(storeApplicationService, times(1)).approve(storeId);
    }

    @Test
    void rejectCreatedStore()throws Exception  {
        // GIVEN
        String storeId = "123";
        String reason="등록 서류 부족";

        // Mocking: storeManagementService.rejectCreatedStore() 호출 시 반환값 없음
        doNothing().when(storeApplicationService).reject(storeId,reason);

        // WHEN & THEN
        mockMvc.perform(put("/api/store/reject/" + storeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reason)) // 요청 본문에 사유 전달
                .andExpect(status().isOk())
                .andExpect(content().string("가게 등록을 거절했습니다. 사유: " + reason));

        // 서비스 메서드 호출 검증

    }

    @Test
    void approveClosedStore()throws Exception  {
        // GIVEN
        String storeId = "123";

        // Mocking: storeManagementService.approveClosedStore() 호출 시 반환값 없음
        doNothing().when(storeApplicationService).close(storeId);

        // WHEN & THEN
        mockMvc.perform(put("/api/store/closure/approve/" + storeId))
                .andExpect(status().isOk())
                .andExpect(content().string("가게 폐업 신청을 승인했습니다."));

        // 서비스 메서드 호출 검증
        verify(storeApplicationService, times(1)).close(storeId);
    }


    @Test
    void getStore() throws Exception {
        // GIVEN
        String storeId = "123";
        AdminStoreResponseDto mockResponse = new AdminStoreResponseDto(
                "123",
                "Store A",
                "010-1234-5678",
                "서울시영등포구 도신로 29길 28",
                "닭갈비 맛 끝내줍니다! 어서오세요 단체주문 환영",
                "한식",
                "한식",
                "한식",
                ".....pdf",
                "..png",
                "mmm.img",
                StoreStatus.OPEN
        );

        when(storeApplicationService.getStore(storeId)).thenReturn(mockResponse);

        // WHEN & THEN
        mockMvc.perform(get("/api/store/" + storeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").value("123"))
                .andExpect(jsonPath("$.storeName").value("Store A"))
                .andExpect(jsonPath("$.category1").value("한식"))
                .andExpect(jsonPath("$.address").value("서울시영등포구 도신로 29길 28"))
                .andExpect(jsonPath("$.storeStatus").value("OPEN"));

        verify(storeApplicationService, times(1)).getStore(storeId);
    }

    @Test
    void getStores() throws Exception {
        // GIVEN
        String status = "ENTIRE";
        int page = 1;
        int size = 5;

        // Mock 데이터 생성
        List<AdminStoreListResponseDto> mockStores = Arrays.asList(
                new AdminStoreListResponseDto(
                        "123",
                        "Store A",
                        "010-1234-5678",
                        "닭갈비 맛 끝내줍니다! 어서오세요 단체주문 환영",
                        "서울시영등포구 도신로 29길 28",
                        StoreStatus.OPEN,
                        "..png",
                        "김사장",
                        LocalDateTime.of(2024, 12, 31, 23, 59)),
                new AdminStoreListResponseDto( "234",
                        "Store B",
                        "010-1234-2234",
                        "보쌈 맛 끝내줍니다! 어서오세요 단체주문 환영",
                        "서울시영등포구 도신로 29길 28",
                        StoreStatus.CLOSED,
                        "..png",
                        "박사장",
                        LocalDateTime.of(2024, 12, 31, 23, 59))
        );

        Page<AdminStoreListResponseDto> mockPage = new PageImpl<>(mockStores, PageRequest.of(page - 1, size), mockStores.size());

        // Mock 설정
        when(storeApplicationService.getStores(status, page, size)).thenReturn(mockPage);

        // WHEN & THEN
        mockMvc.perform(get("/api/store")
                        .param("status", status)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].storeId").value("123"))
                .andExpect(jsonPath("$.content[0].storeName").value("Store A"))
                .andExpect(jsonPath("$.content[0].storeStatus").value("OPEN"))
                .andExpect(jsonPath("$.content[1].storeId").value("234"))
                .andExpect(jsonPath("$.content[1].storeName").value("Store B"))
                .andExpect(jsonPath("$.content[1].storeStatus").value("CLOSED"))
                .andExpect(jsonPath("$.totalElements").value(mockStores.size()))
                .andExpect(jsonPath("$.number").value(page - 1))
                .andExpect(jsonPath("$.size").value(size));

        verify(storeApplicationService, times(1)).getStores(status, page, size);
    }
}