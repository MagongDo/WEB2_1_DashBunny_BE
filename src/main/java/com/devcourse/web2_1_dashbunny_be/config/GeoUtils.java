package com.devcourse.web2_1_dashbunny_be.config;

public class GeoUtils {

  private static final double EARTH_RADIUS_KM = 6371.0; // 지구 반지름 (km)

  public static double getUsersWithinRadius(double storeLat, double storeLon, double userLat, double userLon) {
    // 반경 내 사용자 좌표 저장 리스트
    // 두 좌표 간 거리 계산 (Haversine 공식)
    double latDistance = Math.toRadians(userLat - storeLat);
    double lonDistance = Math.toRadians(userLon - storeLon);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(storeLat)) * Math.cos(Math.toRadians(userLat))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return EARTH_RADIUS_KM * c; // 거리 계산 (단위: km)
    // 반경 내 사용자 좌표 추가
    }
}