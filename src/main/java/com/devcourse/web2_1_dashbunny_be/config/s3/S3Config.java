package com.devcourse.web2_1_dashbunny_be.config.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AWS S3 클라이언트를 애플리케이션 전역에서 재사용 가능하도록 설정
 * </p>
 * AWS 인증 정보 및 리전 설정
 * S3 클라이언트 객체 생성.
 */
@Configuration
public class S3Config {
  /**
  * application.yml에서 accessKey 값 가져오기.
  */
  @Value("${cloud.aws.credentials.access-key}")
  private String accessKey;
  /**
   * application.yml에서 secretKey 값 가져오기.
   */
  @Value("${cloud.aws.credentials.secret-key}")
  private String secretKey;
  /**
   * application.yml에서 region 값 가져오기.
   */
  @Value("${cloud.aws.region.static}")
  private String region;

  /**
   * S3 클라이언트 객체 생성 반환.
   * 다른 클래스에서 의존 받아 사용 가능
   */
  @Bean
  public AmazonS3 amazonS3Client() {
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    return AmazonS3ClientBuilder.standard()
              .withRegion(region)
              .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
              .build();
  }

}
