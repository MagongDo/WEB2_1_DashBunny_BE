package com.devcourse.web2_1_dashbunny_be.annotation.config.lifecycle;



import com.devcourse.web2_1_dashbunny_be.annotation.config.TSID;
import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.UUID;

@Slf4j
public class TSIDListener {

    @PrePersist
    public void prePersist(Object entity) {

        try {
            // 엔티티 클래스의 모든 필드를 가져옴
            Field[] fields = entity.getClass().getDeclaredFields();
            for (Field field : fields) {
                //가져온 필드 중 TSID 어노테이션이 불으며 타입이 String인 경우를 필터링
                if (field.isAnnotationPresent(TSID.class) && field.getType().equals(String.class)) {
                    // 필드가 private 여도 접근을 허용할 수 있게한다.
                    field.setAccessible(true);
                    //TSID를 만들기
                    String tsid = creatTSID();
                    //TSID를 ID 필드에 할당
                    field.set(entity,tsid);
                }
            }
        }catch (IllegalAccessException e){
            log.error("엔티티 아이디 주입 실패");
            throw new RuntimeException(e);
        }
    }

    private String creatTSID() {
        //현재 시간을 밀리초 단위의 타임스탬프로 반환.
        return Instant.now().toEpochMilli() + UUID.randomUUID().toString();
    }
}
