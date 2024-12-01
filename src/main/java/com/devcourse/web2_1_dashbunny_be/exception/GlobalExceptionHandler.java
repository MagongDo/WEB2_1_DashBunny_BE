
package com.devcourse.web2_1_dashbunny_be.exception;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

//** 전역 에러 클래스.

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 유효성 검증 실패 시 처리
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders headers,
          HttpStatus status,
          WebRequest request) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) ->{
      String fieldName = ((FieldError) error).getField();
      String message = error.getDefaultMessage();
      errors.put(fieldName, message);
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
//** NullPointerException.


  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
    return new ResponseEntity<>("요청을 완료하지 못했습니다. 입력 값을 확인하고 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
  }

//** IllegalArgumentException.


  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return new ResponseEntity<>("입력한 정보가 올바르지 않습니다. 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
  }

//** IllegalStateException.


  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
    return new ResponseEntity<>("요청을 처리할 수 없는 상태입니다. 잠시 후 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
  }

//** IOException.


  @ExceptionHandler(IOException.class)
  public ResponseEntity<String> handleIOException(IOException ex) {
    return new ResponseEntity<>("파일 업로 중 오류가 발생했습니다.다시 시도해주세요.", HttpStatus.BAD_REQUEST);
  }

//** SQLException.


  @ExceptionHandler(SQLException.class)
   public ResponseEntity<String> handleSQLException(SQLException ex) {
    return new ResponseEntity<
            >("데이터 처리 중 오류가 발생했습니다. 문제가 지속되면 관리자에게 문의하세요.", HttpStatus.BAD_REQUEST);
  }

//** Exception.


//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> handleAllExceptions(Exception ex) {
//    return new ResponseEntity<>("서버 오류가 발생했습니다. 관리자에게 문의해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
//  }
  // RuntimeException 처리
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException e){
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
  }
  // NumberFormatException 처리
  @ExceptionHandler(NumberFormatException.class)
  public ResponseEntity<String> handleNumberFormatException(NumberFormatException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid number format: " + e.getMessage());
  }

    @ExceptionHandler(DifferentStoreException.class)
    public ResponseEntity<String> handleDifferentStoreException(DifferentStoreException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handleCustomException(CustomException e) {


    return ResponseEntity.status(e.getStatus())
            .body(Collections.singletonMap("error", e.getMessage()));
  }
}

//package com.devcourse.web2_1_dashbunny_be.exception;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
///**
// * 전역 에러 클래스.
// */
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//  /**
//  * NullPointerException.
//  */
//  @ExceptionHandler(NullPointerException.class)
//  public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
//    return new ResponseEntity<>("요청을 완료하지 못했습니다. 입력 값을 확인하고 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
//  }
//
//  /**
//  * IllegalArgumentException.
//  */
//  @ExceptionHandler(IllegalArgumentException.class)
//  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
//    return new ResponseEntity<>("입력한 정보가 올바르지 않습니다. 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
//  }
//
//  /**
//  * IllegalStateException.
//  */
//  @ExceptionHandler(IllegalStateException.class)
//  public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
//    return new ResponseEntity<>("요청을 처리할 수 없는 상태입니다. 잠시 후 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
//  }
//
//  /**
//  * IOException.
//  */
//  @ExceptionHandler(IOException.class)
//  public ResponseEntity<String> handleIOException(IOException ex) {
//    return new ResponseEntity<>("파일 업로 중 오류가 발생했습니다.다시 시도해주세요.", HttpStatus.BAD_REQUEST);
//  }
//
//  /**
//  * SQLException.
//  */
//  @ExceptionHandler(SQLException.class)
//   public ResponseEntity<String> handleSQLException(SQLException ex) {
//    return new ResponseEntity<
//            >("데이터 처리 중 오류가 발생했습니다. 문제가 지속되면 관리자에게 문의하세요.", HttpStatus.BAD_REQUEST);
//  }
//
//  /**
//   * Exception.
//   */
//  @ExceptionHandler(Exception.class)
//  public ResponseEntity<String> handleAllExceptions(Exception ex) {
//    return new ResponseEntity<>("서버 오류가 발생했습니다. 관리자에게 문의해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
//  }
//
//
//  @ExceptionHandler(CustomException.class)
//  public ResponseEntity<String> handleCustomException(CustomException ex) {
//    return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
//  }
//
//}
