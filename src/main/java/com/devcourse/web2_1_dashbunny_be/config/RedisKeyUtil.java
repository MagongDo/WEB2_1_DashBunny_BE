package com.devcourse.web2_1_dashbunny_be.config;

import org.springframework.stereotype.Component;

@Component
public class RedisKeyUtil {

    // 소수점 자릿수를 제한하여 좌표를 문자열로 변환
    private static String formatCoordinate(double value) {
        return String.format("%.6f", value);
    }

    // 사용자 ID와 좌표를 조합하여 키 생성
    public static String generateKey(String userId, double latitude, double longitude) {
        String lat = formatCoordinate(latitude);
        String lon = formatCoordinate(longitude);
        return String.format("user:%s:location:%s:%s:store", userId, lat, lon);
    }
}