package com.devcourse.web2_1_dashbunny_be.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuGroup;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuWithMenuGroupResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.UpdateMenuRequestDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.dto.menu.MenuGroupResponseDto;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuGroupRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;

import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.MenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuServiceImplTest2 {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuGroupRepository groupRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // UpdateAll 테스트
    @Test
    void updateAll_ShouldUpdateMenuFields() {
        // Arrange
        Long menuId = 1L;
        UpdateMenuRequestDto updateDto = new UpdateMenuRequestDto();
        updateDto.setMenuName("Updated Menu");
        updateDto.setMenuGroupId(2L);
        updateDto.setPrice(12000L);
        updateDto.setMenuContent("Updated Content");
        updateDto.setStockAvailable(true);
        updateDto.setMenuStock(50L);
        updateDto.setIsSoldOut(false);

        MenuManagement menu = new MenuManagement();
        menu.setMenuId(menuId);
        menu.setMenuName("Original Menu");
        menu.setPrice(10000L);

        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setGroupId(2L);
        menuGroup.setGroupName("Updated Group");

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(groupRepository.findById(updateDto.getMenuGroupId())).thenReturn(Optional.of(menuGroup));

        // Act
        menuService.updateAll(menuId, updateDto);

        // Assert
        assertEquals("Updated Menu", menu.getMenuName());
        assertEquals(menuGroup, menu.getMenuGroup());
        assertEquals(12000L, menu.getPrice());
        assertEquals("Updated Content", menu.getMenuContent());
        assertTrue(menu.isStockAvailable());
        assertEquals(50, menu.getMenuStock());
        assertFalse(menu.getIsSoldOut());

        verify(menuRepository, times(1)).save(menu);
    }

    @Test
    void updateAll_ShouldThrowException_WhenMenuNotFound() {
        // Arrange
        Long menuId = 1L;
        UpdateMenuRequestDto updateDto = new UpdateMenuRequestDto();

        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> menuService.updateAll(menuId, updateDto));
        assertEquals("메뉴를 찾을 수 없습니다.", exception.getMessage());
        verify(menuRepository, never()).save(any());
    }

    // MenuWithGroups 테스트
    @Test
    void menuWithGroups_ShouldReturnMenuAndGroups() {
        // Arrange
        Long menuId = 1L;

        MenuManagement menu = new MenuManagement();
        menu.setMenuId(menuId);
        menu.setMenuName("Sample Menu");

        MenuGroup group1 = new MenuGroup();
        MenuGroup group2 = new MenuGroup();
        List<MenuGroup> menuGroups = Arrays.asList(group1, group2);

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(groupRepository.findAll()).thenReturn(menuGroups);

        // Act
        MenuWithMenuGroupResponseDto responseDto = menuService.MenuWithGroups(menuId);

        // Assert
        assertNotNull(responseDto);
        assertEquals(menuId, responseDto.getMenu().getMenuId());
        assertEquals(2, responseDto.getMenuGroups().size());
        assertEquals("Group 1", responseDto.getMenuGroups().get(0).getGroupName());

        verify(menuRepository, times(1)).findById(menuId);
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    void menuWithGroups_ShouldThrowException_WhenMenuNotFound() {
        // Arrange
        Long menuId = 1L;

        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> menuService.MenuWithGroups(menuId));
        assertEquals("메뉴를 찾을 수 없습니다.", exception.getMessage());

        verify(groupRepository, never()).findAll();
    }
}