package com.devcourse.web2_1_dashbunny_be.config.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class fileUploadService {

  private final AmazonS3 amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  public String uploadFile(MultipartFile file, String fileType) throws IOException {

    if (file.getSize() > 5 * 1024 * 1024) {
      throw new IllegalArgumentException("파일 크기가 5MB를 초과합니다.");
    }

    /**
    * 파일 이름 생성
    * 각 특성별로 줄 수 있다? = userImage / storeImage / adminImage -> fileType.
    */
    String fileName = fileType + "/" + System.currentTimeMillis() + file.getOriginalFilename();

    // 파일 메타데이터 설정
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());

    try {
    // S3에 파일 업로드
      amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
    } catch (AmazonServiceException e) {
      throw new RuntimeException("AWS S3 업로드 실패: " + e.getMessage(), e);
    } catch (SdkClientException e) {
      throw new RuntimeException("AWS SDK 오류: " + e.getMessage(), e);
    }

    // 업로드된 파일 URL 생성
    return amazonS3Client.getUrl(bucketName, fileName).toString();
    }
}
