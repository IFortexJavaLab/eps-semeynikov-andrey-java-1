package com.ifortex.internship.exception.custom;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.exception.codes.ErrorCode;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CourseDtoValidationException extends RuntimeException {
  private final ErrorCode errorCode;
  private final Set<String> violationMessages;

  public CourseDtoValidationException(Set<ConstraintViolation<CourseDto>> violations) {
    super(ErrorCode.COURSE_DTO_VALIDATION_FAILED.getDefaultMessage());

    this.violationMessages =
        violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
    this.errorCode = ErrorCode.COURSE_DTO_VALIDATION_FAILED;
  }
}
