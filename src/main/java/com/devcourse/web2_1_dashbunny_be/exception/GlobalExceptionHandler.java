package com.devcourse.web2_1_dashbunny_be.exception;

import java.io.IOException;
import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 에러 클래스.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
  * NullPointerException.
  */
  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
    return new ResponseEntity<>("요청을 완료하지 못했습니다. 입력 값을 확인하고 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
  }

  /**
  * IllegalArgumentException.
  */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ResponseEntity<>("입력한 정보가 올바르지 않습니다. 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
  }

  /**
  * IllegalStateException.
  */
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
    return new ResponseEntity<>("요청을 처리할 수 없는 상태입니다. 잠시 후 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
  }

  /**
  * IOException.
  */
  @ExceptionHandler(IOException.class)
  public ResponseEntity<String> handleIOException(IOException ex) {
    return new ResponseEntity<>("파일 업로 중 오류가 발생했습니다.다시 시도해주세요.", HttpStatus.BAD_REQUEST);
  }

  /**
  * SQLException.
  */
  @ExceptionHandler(SQLException.class)
   public ResponseEntity<String> handleSQLException(SQLException ex) {
    return new ResponseEntity<
            >("데이터 처리 중 오류가 발생했습니다. 문제가 지속되면 관리자에게 문의하세요.", HttpStatus.BAD_REQUEST);
  }

  /**
   * Exception.
   */
//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> handleAllExceptions(Exception ex) {
//    return new ResponseEntity<>("서버 오류가 발생했습니다. 관리자에게 문의해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
//  }

}
