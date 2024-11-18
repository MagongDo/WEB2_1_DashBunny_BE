package com.devcourse.web2_1_dashbunny_be.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)// 해당 어노테이션의 적용 범위 설정을 위한 어노테이션 (필드의 변수에만 지정가능하다.)
@Retention(RetentionPolicy.RUNTIME) // 해당 어노테이션의 유지되는 시점 (런타임에 어노테이션 정보를 유지한다. )
public @interface TSID{
}

//@Interface : 자바에서 새로운 어노테이션 타입을 적용할때 사용된다. 클래스처럼 정의 되며 해당 클래스에는 어노테이션의 행동에 대한 내용을 담지는 않는다.
// 행동 -> 리스너 클래스(행동 정의 클래스)를 만들어 함께 사용한다.
// 이유 -> 단일 책임의 원칙을 지키기 위해서라고 생각하면 된다.