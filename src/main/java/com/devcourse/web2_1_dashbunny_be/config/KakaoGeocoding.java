package com.devcourse.web2_1_dashbunny_be.config;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoGeocoding {

  @Value("${kakao.api.key}")
  private String kakaoApiKey; // 인스턴스 필드로 변경

  public JsonObject getCoordinatesFromAddress(String address) throws Exception {
    OkHttpClient client = new OkHttpClient();
    String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
    String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encodedAddress;
    Request request = new Request.Builder()
              .url(url)
              .addHeader("Authorization", "KakaoAK " + kakaoApiKey)
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
