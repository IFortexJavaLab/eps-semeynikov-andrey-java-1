package com.ifortex.internship.exception.custom;

import com.ifortex.internship.exception.codes.ErrorCode;
import lombok.Getter;

@Getter
public class CourseIsNotOpenedException extends RuntimeException {
  private final ErrorCode errorCode;
  private final String message;

  public CourseIsNotOpenedException(ErrorCode errorCode, String message) {
    super(String.format("%s (%s)", errorCode.getDefaultMessage(), message));
    this.errorCode = errorCode;
    this.message = String.format("%s (%s)", errorCode.getDefaultMessage(), message);
  }
}
