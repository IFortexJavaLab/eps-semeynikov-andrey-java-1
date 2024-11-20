package com.ifortex.internship.exception.custom;

import com.ifortex.internship.exception.codes.ErrorCode;
import lombok.Getter;

@Getter
public class EnrollmentException extends RuntimeException {
  private final ErrorCode errorCode;
  private final String message;

  public EnrollmentException(ErrorCode errorCode, String message) {
    super(String.format("%s (%s)", errorCode.getDefaultMessage(), message));
    this.errorCode = errorCode;
    this.message = String.format("%s (%s)", errorCode.getDefaultMessage(), message);
  }
}
