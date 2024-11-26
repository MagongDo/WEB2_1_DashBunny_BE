package com.devcourse.web2_1_dashbunny_be.service;

import com.devcourse.web2_1_dashbunny_be.domain.owner.MenuManagement;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.repository.MenuRepository;
import com.devcourse.web2_1_dashbunny_be.feature.owner.menu.service.MenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    private MenuManagement menu1;
    private MenuManagement menu2;

    @BeforeEach
    void setUp() {
        // 테스트용 메뉴 데이터 설정
        menu1 = new MenuManagement();
        menu1.setMenuId(1L);
        menu1.setMenuName("코코 샐러드");
        menu1.setPrice(9500L);

        menu2 = new MenuManagement();
        menu2.setMenuId(2L);
        menu2.setMenuName("코코 샐러드 2");
        menu2.setPrice(10500L);
    }

    @Test
    void testFindSearchMenuName() {
        // "코코 샐러드"를 포함하는 메뉴 목록 반환하도록 mock 설정
        when(menuRepository.findByMenuNameContaining("코코 샐러드")).thenReturn(List.of(menu1, menu2));

        // 서비스 메소드 호출
        List<MenuManagement> foundMenus = menuService.findSearchMenuName("코코 샐러드");

        // 결과 검증
        assertEquals(2, foundMenus.size());
        assertEquals("코코 샐러드", foundMenus.get(0).getMenuName());
        assertEquals("코코 샐러드 2", foundMenus.get(1).getMenuName());
    }
}