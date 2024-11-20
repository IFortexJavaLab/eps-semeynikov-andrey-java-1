package com.ifortex.internship.exception.custom;

import com.ifortex.internship.exception.codes.ErrorCode;
import lombok.Getter;

@Getter
public class EnrollmentLimitExceededException extends RuntimeException {
  private final ErrorCode errorCode;
  private final long courseId;

  public EnrollmentLimitExceededException(ErrorCode errorCode, long courseId) {
    super(String.format("%s (id = %d)", errorCode.getDefaultMessage(), courseId));
    this.errorCode = errorCode;
    this.courseId = courseId;
  }
}
