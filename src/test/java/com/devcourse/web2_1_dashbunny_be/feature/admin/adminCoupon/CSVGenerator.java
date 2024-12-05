package com.devcourse.web2_1_dashbunny_be.feature.admin.adminCoupon;

import java.io.FileWriter;
import java.io.IOException;

public class CSVGenerator {
    public static void main(String[] args) {
        String csvFile = "users.csv";
        try (FileWriter writer = new FileWriter(csvFile)) {
            // 헤더 작성
            writer.append("phone,password\n");

            // 1000명의 사용자 생성
            for (int i = 0; i <= 1000; i++) {
                String phone = String.format("010%08d", i); // 010으로 시작하고 8자리로 포맷
                String password = "testPassword" + i;
                writer.append(phone).append(",").append(password).append("\n");
            }
            System.out.println("CSV 파일이 성공적으로 생성되었습니다: " + csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
