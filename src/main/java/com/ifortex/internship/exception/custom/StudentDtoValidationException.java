package com.ifortex.internship.exception.custom;

import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.exception.codes.ErrorCode;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class StudentDtoValidationException extends RuntimeException {
  private final ErrorCode errorCode;
  private final Set<String> violationMessages;

  public StudentDtoValidationException(Set<ConstraintViolation<StudentDto>> violations) {
    super(ErrorCode.STUDENT_DTO_VALIDATION_FAILED.getDefaultMessage());

    this.violationMessages =
        violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
    this.errorCode = ErrorCode.STUDENT_DTO_VALIDATION_FAILED;
  }
}
