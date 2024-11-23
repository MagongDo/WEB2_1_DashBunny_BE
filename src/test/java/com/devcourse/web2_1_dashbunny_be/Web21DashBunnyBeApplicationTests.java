package com.devcourse.web2_1_dashbunny_be;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EnableJpaAuditing
class Web21DashBunnyBeApplicationTests {

  @Test
  void contextLoads() {
  }

}
