package com.devcourse.web2_1_dashbunny_be.domain.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class MenuManagementTest {
//메뉴와 메뉴 그룹에 대한 테스트
    // 메뉴는 그룹이 없어도 생성할 수 있다.
    // 그룹에는 하나 이상의 메뉴가 들어갈 수 있다.
    // 그룹을 삭제하면 메뉴가 다 삭제 된다.
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testH2Save(){
        //given 그룹이 없는 메뉴 엔티티
        MenuManagement menu = new MenuManagement();
        menu.setStoreId("store1");
        menu.setMenuName("치킨");
        menu.setMenuContent("바삭 바삭한 치킨");
        menu.setStockAvailable(true);
        menu.setMenuStock(1);
        entityManager.persist(menu);
        entityManager.flush();
        entityManager.clear();

        MenuManagement menu2 = entityManager.find(MenuManagement.class, menu.getMenuId());
        //그룹이 없는 메뉴 엔티티가 존재하는가
        assertNotNull(menu2);
        //에뉴 그룹이 널인가
        assertNull(menu2.getMenuGroup());
    }

    //그룹이 없는 메뉴에 추후 그룹에 속하게 하기
    @Test
    public void testH2addGroup(){
        MenuManagement menu = new MenuManagement();
        menu.setStoreId("store1");
        menu.setMenuName("치킨");
        menu.setMenuContent("바삭 바삭한 치킨");
        menu.setStockAvailable(true);
        menu.setMenuStock(1);
        entityManager.persist(menu);

        MenuGroup group = new MenuGroup();
        group.setStoreId("store1");
        group.setGroupName("양식");
        menu.setMenuGroup(group);
        group.getMenuList().add(menu);
        entityManager.persist(group);
        entityManager.flush();
        entityManager.clear();

        MenuGroup group2 = entityManager.find(MenuGroup.class, group.getGroupId());
        assertNotNull(group2);
        assertNotNull(group2.getMenuList());
        assertEquals("치킨", group2.getMenuList().get(0).getMenuName());
    }

    //그룹은 삭제 되어도 메뉴는 남아있게 하기
    @Test
    public void testH2removeGroup(){
        // Given: 그룹과 메뉴 생성
        MenuManagement menu = new MenuManagement();
        menu.setStoreId("store1");
        menu.setMenuName("치킨");
        menu.setMenuContent("바삭 바삭한 치킨");
        menu.setStockAvailable(true);
        menu.setMenuStock(1);

        MenuGroup group = new MenuGroup();
        group.setStoreId("store1");
        group.setGroupName("양식");

        group.getMenuList().add(menu);
        menu.setMenuGroup(group);

        entityManager.persist(group);
        entityManager.persist(menu);
        entityManager.flush();
        entityManager.clear();

        // When: 그룹 삭제 전에 관계 해제
        MenuGroup persistedGroup = entityManager.find(MenuGroup.class, group.getGroupId());
        assertNotNull(persistedGroup);

        persistedGroup.getMenuList().forEach(menuItem -> {
            menuItem.setMenuGroup(null); // 관계 해제
            entityManager.persist(menuItem); // 업데이트 반영
        });

        // 그룹 삭제
        entityManager.remove(persistedGroup);
        entityManager.flush();
        entityManager.clear();

        // Then: 메뉴는 남아있음
        MenuManagement persistedMenu = entityManager.find(MenuManagement.class, menu.getMenuId());
        assertNotNull(persistedMenu); // 메뉴는 여전히 존재
        assertNull(persistedMenu.getMenuGroup()); // 그룹은 삭제되어 관계는 null
    }


}