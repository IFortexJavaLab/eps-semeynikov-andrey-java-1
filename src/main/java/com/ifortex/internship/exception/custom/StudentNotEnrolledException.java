package com.ifortex.internship.exception.custom;

import com.ifortex.internship.exception.codes.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentNotEnrolledException extends RuntimeException {
  private final ErrorCode errorCode;
  private final long studentId;
  private final long courseId;

  public StudentNotEnrolledException(ErrorCode errorCode, long courseId, long studentId) {
    super(
        String.format(
            "%s (courseId = %d, studentId = %d)",
            errorCode.getDefaultMessage(), courseId, studentId));
    this.errorCode = errorCode;
    this.courseId = courseId;
    this.studentId = studentId;
  }
}
