package com.devcourse.web2_1_dashbunny_be.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
//throw new CustomException("메세지", HttpStatus.BAD_REQUEST)로 작성하시면 됩니다!!!