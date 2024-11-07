package com.ifortex.internship.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFound(
      ResourceNotFoundException ex, WebRequest request) {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("errorMessage", ex.getMessage());
    responseBody.put("errorCode", ex.getErrorCode());
    responseBody.put("timestamp", System.currentTimeMillis());
    return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
  }
}
