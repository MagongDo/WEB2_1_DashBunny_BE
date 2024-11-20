package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// 메뉴 그룹 정보를 관리하는 엔티티 클래스
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_group")
public class MenuGroup {

    // 그룹 고유키 (자동 생성)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    // 가게 ID (필수)
    @Column(nullable = false)
    private String storeId;

    // 그룹 이름 (필수, 최대 길이 255자)
    @Column(nullable = false, length = 255)
    private String groupName;

    // MenuManagement와의 연관관계 (1:N)
    @OneToMany(mappedBy = "menuGroup", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MenuManagement> menuList = new ArrayList<>();

}
//그룹 삭제시 명시적으로 그룹 삭제 전에 관계 해제하는 과정을 서비스 단에서 추가해 주어야 한다.