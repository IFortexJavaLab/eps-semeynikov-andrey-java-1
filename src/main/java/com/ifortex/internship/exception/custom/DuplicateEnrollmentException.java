package com.ifortex.internship.exception.custom;

import com.ifortex.internship.exception.codes.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateEnrollmentException extends RuntimeException {
  private final ErrorCode errorCode;
  private final long studentId;
  private final long courseId;

  public DuplicateEnrollmentException(ErrorCode errorCode, long courseId, long studentId) {
    super(
        String.format(
            "%s (courseId = %d, studentId = %d)",
            errorCode.getDefaultMessage(), courseId, studentId));
    this.errorCode = errorCode;
    this.courseId = courseId;
    this.studentId = studentId;
  }
}
