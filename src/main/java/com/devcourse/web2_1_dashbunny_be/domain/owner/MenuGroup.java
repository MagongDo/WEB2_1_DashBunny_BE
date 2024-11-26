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
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreManagement storeId;

    // 그룹 이름 (필수, 최대 길이 255자)
    @Column(nullable = false, length = 255)
    private String groupName;

    // 대표 그룹 여부 (true이면 대표 그룹)
    @Column(nullable = false)
    private Boolean isMainGroup = false; // 기본값 false로 설정

    // MenuManagement와의 연관관계 (1:N)
    @OneToMany(mappedBy = "menuGroup", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Column(nullable = true)
    private List<MenuManagement> menuList = new ArrayList<>();



}