package com.devcourse.web2_1_dashbunny_be.domain.user.role;

public enum DeclineReasonType {
    OUT_OF_STOCK("재고 부족"),
    STORE_CLOSED("가게 영업 종료"),
    HIGH_ORDER_VOLUME("주문량 과다"),
    DELIVERY_ISSUE("배달 문제"),
    MENU_UNAVAILABLE("메뉴 준비 불가"),
    OTHER("기타 사유");

    private final String description;

    DeclineReasonType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
