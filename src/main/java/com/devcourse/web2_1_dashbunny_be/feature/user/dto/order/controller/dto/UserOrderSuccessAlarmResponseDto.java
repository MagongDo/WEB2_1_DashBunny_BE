package com.devcourse.web2_1_dashbunny_be.feature.user.dto.order.controller.dto;

/**
 * 주문내역 요청과 동시에 사용자에게 전달되는 알람 dto.
 * 요청이 들어오면 정보를 db에 저장하기 전에 알람을 보낸다.
 */
public class UserOrderSuccessAlarmResponseDto {
    // 어떤 내용이 담겨야 할까? 사용자의 알람 화면에서 담길 것들
    // 성공 메세지 / 아니라 .. 주문 접수 중 알람 메시지가 전달 "/
    //굳이 필요 없다.
    // 주문 생성 시간
    // 주문 상태 : 조리중
}
// 즉 알람을 바로 보냄 과 동시에 주문 내역에 대한 정보를 서비스로 옮김
// 사장님한테는 언제 그 정보가 가야하지?
// 정보가 저장이 된 후에?_> 사장님에게 알람
// 사장님이 승인을 누르든 거절을 누르던 db에 그 상황이 연결 되지 전에 사용자에게 접수 여부를 전달
