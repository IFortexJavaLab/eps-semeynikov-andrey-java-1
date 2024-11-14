package com.ifortex.internship.exception.handler;

import com.ifortex.internship.exception.custom.CourseDtoValidationException;
import com.ifortex.internship.exception.custom.EnrollmentException;
import com.ifortex.internship.exception.custom.EnrollmentLimitExceededException;
import com.ifortex.internship.exception.custom.ResourceNotFoundException;
import com.ifortex.internship.exception.custom.StudentDtoValidationException;
import com.ifortex.internship.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for managing application-specific exceptions and providing consistent
 * error responses.
 *
 * <p>This class is annotated with {@link RestControllerAdvice}, allowing it to handle exceptions
 * across all controllers in the application. Each handler method is designed to process a specific
 * exception type, generate a corresponding {@link ErrorResponse}, and return an appropriate HTTP
 * status.
 *
 * <p>Each handler constructs a response with an error message and a custom error code, ensuring a
 * uniform error structure throughout the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link ResourceNotFoundException} and returns a 404 Not Found status.
   *
   * @param ex the exception indicating a requested resource was not found
   * @return a {@link ResponseEntity} containing the ErrorResponse with 404 status
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getErrorCode().getCode());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  /**
   * Handles {@link EnrollmentLimitExceededException} and returns a 403 Forbidden status.
   *
   * @param ex the exception indicating enrollment limit for a course was exceeded
   * @return a ResponseEntity containing the ErrorResponse with 403 status
   */
  @ExceptionHandler(EnrollmentLimitExceededException.class)
  public ResponseEntity<ErrorResponse> handleEnrollmentLimitExceeded(
      EnrollmentLimitExceededException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getErrorCode().getCode());
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  /**
   * Handles {@link StudentDtoValidationException} and returns a 406 Not Acceptable status with
   * validation error details.
   *
   * <p>Constructs a comprehensive error message from validation violations associated with the
   * StudentDtoValidationException.
   *
   * @param ex the exception containing validation errors for a student DTO
   * @return a ResponseEntity containing the ErrorResponse with 406 status
   */
  @ExceptionHandler(StudentDtoValidationException.class)
  public ResponseEntity<ErrorResponse> handleStudentDtoValidationException(
      StudentDtoValidationException ex) {

    StringBuilder fullMessageBuilder =
        new StringBuilder(ex.getErrorCode().getDefaultMessage()).append(": ");

    ex.getViolationMessages()
        .forEach(violation -> fullMessageBuilder.append(violation).append(", "));

    ErrorResponse response =
        new ErrorResponse(fullMessageBuilder.toString().trim(), ex.getErrorCode().getCode());
    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
  }

  /**
   * Handles {@link CourseDtoValidationException} and returns a 406 Not Acceptable status with
   * validation error details.
   *
   * <p>Constructs a detailed error message from validation violations associated with the
   * CourseDtoValidationException.
   *
   * @param ex the exception containing validation errors for a course DTO
   * @return a ResponseEntity containing the ErrorResponse with 406 status
   */
  @ExceptionHandler(CourseDtoValidationException.class)
  public ResponseEntity<ErrorResponse> handleCourseDtoValidationException(
      CourseDtoValidationException ex) {

    StringBuilder fullMessageBuilder =
        new StringBuilder(ex.getErrorCode().getDefaultMessage()).append(": ");

    ex.getViolationMessages()
        .forEach(violation -> fullMessageBuilder.append(violation).append(", "));

    ErrorResponse response =
        new ErrorResponse(fullMessageBuilder.toString().trim(), ex.getErrorCode().getCode());
    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
  }

  /**
   * Handles {@link EnrollmentException} and returns a 409 Conflict status.
   *
   * @param ex the exception indicating a general enrollment conflict
   * @return a ResponseEntity containing the ErrorResponse with 409 status
   */
  @ExceptionHandler(EnrollmentException.class)
  public ResponseEntity<ErrorResponse> handleEnrollmentException(EnrollmentException ex) {
    ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getErrorCode().getCode());
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }
}
