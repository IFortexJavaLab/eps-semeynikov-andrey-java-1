package com.ifortex.internship.exception.codes;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.model.Course;
import com.ifortex.internship.model.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing custom error codes and default messages for specific error scenarios within the
 * application.
 *
 * <p>Each error code consists of a unique integer code and a default message that provides
 * information about the type of error encountered. These codes help categorize errors and make it
 * easier to handle specific cases in the application, such as resource not found, validation
 * failures, and enrollment-related errors.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

  /** Error code indicating that a requested {@link Student} resource was not found. */
  STUDENT_NOT_FOUND(40401, "Requested resource not found"),

  /** Error code indicating that a requested {@link Course} resource was not found. */
  COURSE_NOT_FOUND(40402, "Requested resource not found"),

  /** Error code indicating that the enrollment limit has been exceeded for the specified Course. */
  ENROLLMENT_LIMIT_EXCEEDED(40302, "Enrollment limit exceeded for the course"),

  /** Error code indicating a failure during the enrollment process. */
  ENROLLMENT_FAILED(40902, "Error during an enrollment"),

  /** Error code indicating a validation failure for the {@link StudentDto}. */
  STUDENT_DTO_VALIDATION_FAILED(40601, "Validation failed for the provided data"),

  /** Error code indicating a validation failure for the {@link CourseDto}. */
  COURSE_DTO_VALIDATION_FAILED(40602, "Validation failed for the provided data"),

  /** Error code indicating a failure when attempting change course after start */
  COURSE_HAS_ALREADY_STARTED(40602, "Course has already started");

  /** The unique error code representing the specific type of error. */
  private final int code;

  /** The default message associated with this error code, providing details of the error. */
  private final String defaultMessage;
}
