package com.devcourse.web2_1_dashbunny_be.feature.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
    }

    public String storeFile(MultipartFile file) throws IOException {
        // 파일 이름 정리
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // 파일 확장자 검증 (선택 사항)
        if (!originalFileName.endsWith(".png") &&
                !originalFileName.endsWith(".jpg") &&
                !originalFileName.endsWith(".jpeg")) {
            throw new IOException("지원되지 않는 파일 형식입니다.");
        }

        // 파일 크기 검증 (예: 1MB 이하)
        if (file.getSize() > 1024 * 1024) {
            throw new IOException("파일 크기가 너무 큽니다. (최대 1MB)");
        }

        // 파일 이름에 UUID 추가하여 중복 방지
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // 파일 저장 경로 설정
        Path targetLocation = this.fileStorageLocation.resolve(fileName);

        // 파일 저장
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // 파일 접근 URL 반환 (예: http://localhost:8080/uploads/profile-pictures/{fileName})
        return "/uploads/profile-pictures/" + fileName;
    }

}
