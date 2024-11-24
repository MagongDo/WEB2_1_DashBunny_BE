package com.devcourse.web2_1_dashbunny_be.domain.owner;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// 메뉴 정보를 관리하는 엔티티 클래스
@Getter
@Setter
@Entity
@Table(name = "menu_management")
public class MenuManagement {

    // 메뉴 고유키 (자동 생성)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    // 가게 고유키 (필수)(수정)
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private StoreManagement storeId;

    // 그룹 고유키 (MenuGroup과 연관 관계 설정)
    // 그룹이 없어도 메뉴는 존재할 수 있다.
    @ManyToOne(optional = true)
    @JoinColumn(name = "group_id", nullable = true)
    private MenuGroup menuGroup;

    // 메뉴 이름 (필수, 최대 길이 255자)
    @Column(nullable = false, length = 255)
    private String menuName;

    // 메뉴 설명 (TEXT 타입)
    @Column(columnDefinition = "TEXT")
    private String menuContent;

    // 메뉴 사진 (파일 경로나 URL, 최대 길이 255자)
    @Column(length = 255)
    private String menuImage;

    // 재고 등록 여부 (boolean 타입)
    @Column(nullable = false)
    private boolean stockAvailable;

    // 메뉴 재고 수량
    private Integer menuStock;

    //품절 여부
    @Column(nullable = false)
    private Boolean isSoldOut = false;

    //상품 금액

    @Column(nullable = false)
    private Long price;









}