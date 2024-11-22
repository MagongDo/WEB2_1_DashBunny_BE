package com.devcourse.web2_1_dashbunny_be.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;

public class KakaoGeocoding {

    @Value("${kakao.api.key}")
    private static String kakaoApiKey;
    private static final String KAKAO_API_KEY = kakaoApiKey; // 카카오 REST API 키


    public static JsonObject getCoordinatesFromAddress(String address) throws Exception {
        OkHttpClient client = new OkHttpClient();

        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "KakaoAK " + KAKAO_API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
                if (json.has("documents") && json.getAsJsonArray("documents").size() > 0) {
                    JsonObject location = json.getAsJsonArray("documents").get(0).getAsJsonObject();
                    JsonObject coordinates = new JsonObject();
                    coordinates.addProperty("latitude", location.get("y").getAsString());
                    coordinates.addProperty("longitude", location.get("x").getAsString());
                    return coordinates;
                }
            }
        }
        return null; // 주소 변환 실패 시
    }
}
