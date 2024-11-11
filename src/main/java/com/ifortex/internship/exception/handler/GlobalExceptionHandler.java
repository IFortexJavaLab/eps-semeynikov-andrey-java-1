package com.ifortex.internship.exception.handler;

import com.ifortex.internship.exception.response.ErrorResponse;
import com.ifortex.internship.exception.custom.DuplicateEnrollmentException;
import com.ifortex.internship.exception.custom.EnrollmentLimitExceededException;
import com.ifortex.internship.exception.custom.ResourceNotFoundException;
import com.ifortex.internship.exception.custom.StudentNotEnrolledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getErrorCode().getCode());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(DuplicateEnrollmentException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateEnrollment(DuplicateEnrollmentException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().getCode());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(StudentNotEnrolledException.class)
  public ResponseEntity<ErrorResponse> handleStudentNotEnrolled(StudentNotEnrolledException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().getCode());
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(EnrollmentLimitExceededException.class)
  public ResponseEntity<ErrorResponse> handleEnrollmentLimitExceeded(
      EnrollmentLimitExceededException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().getCode());
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }
}
