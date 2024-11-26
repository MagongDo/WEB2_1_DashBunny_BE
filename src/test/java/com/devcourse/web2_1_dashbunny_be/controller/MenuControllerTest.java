package com.devcourse.web2_1_dashbunny_be.controller;

import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuListResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.controller.MenuController;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)  // Enable Mockito
public class MenuControllerTest {

    @Mock
    private MenuService menuService;  // Mock the service

    @InjectMocks
    private MenuController menuController;  // Inject the mocks into the controller

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();  // Initialize MockMvc
    }

    @Test
    void testSearchMenu() throws Exception {
        // Prepare response DTOs
        MenuListResponseDto menuDto1 = MenuListResponseDto.builder()
                .menuId(1L)
                .menuName("코코 샐러드")
                .price(9500L)
                .menuGroupName("샐러드")
                .stockAvailable(true)
                .menuStock(10)
                .isSoldOut(false)
                .build();

        MenuListResponseDto menuDto2 = MenuListResponseDto.builder()
                .menuId(2L)
                .menuName("코코 샐러드 2")
                .price(10500L)
                .menuGroupName("샐러드")
                .stockAvailable(true)
                .menuStock(5)
                .isSoldOut(false)
                .build();

        // Mocking the service method
        when(menuService.findSearchMenuName("코코 샐러드"));
             //   .thenReturn(List.of(menuDto1, menuDto2));  // Return mocked data

        // Perform the GET request and validate the response
        mockMvc.perform(get("/api/store/menu/search")
                        .param("menuName", "코코 샐러드")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.menus[0].menuName").value("코코 샐러드"))
                .andExpect(jsonPath("$.menus[1].menuName").value("코코 샐러드 2"))
                .andExpect(jsonPath("$.menus[0].price").value(9500))
                .andExpect(jsonPath("$.menus[1].price").value(10500));
    }
}