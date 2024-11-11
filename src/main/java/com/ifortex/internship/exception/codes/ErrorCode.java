package com.ifortex.internship.exception.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing custom error codes and default messages for specific error scenarios within the
 * application.
 *
 * <p>Each error code consists of a unique integer code and a default message that provides
 * information about the type of error encountered.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

  /**
   * Error code indicating that a requested {@link com.ifortex.internship.model.Student} resource
   * was not found.
   */
  STUDENT_NOT_FOUND(40401, "Requested resource not found"),

  /**
   * Error code indicating that a requested {@link com.ifortex.internship.model.Course} resource was
   * not found.
   */
  COURSE_NOT_FOUND(40402, "Requested resource not found"),

  /**
   * Error code indicating that the enrollment limit has been exceeded for the specified {@link
   * com.ifortex.internship.model.Course}.
   */
  ENROLLMENT_LIMIT_EXCEEDED(40302, "Enrollment limit exceeded for course"),

  /**
   * Error code indicating that a {@link com.ifortex.internship.model.Student} is already enrolled
   * in the specified {@link com.ifortex.internship.model.Course}.
   */
  DUPLICATE_ENROLLMENT(40902, "Student already enrolled in course"),

  /**
   * Error code indicating that the {@link com.ifortex.internship.model.Student} is not enrolled in
   * the specified {@link com.ifortex.internship.model.Course}.
   */
  STUDENT_NOT_ENROLLED(40903, "Student is not enrolled in course");

  /** The unique error code representing the specific type of error. */
  private final int code;

  /** The default message associated with this error code, providing details of the error. */
  private final String defaultMessage;
}
