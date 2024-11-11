package com.ifortex.internship.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * A data transfer object (DTO) representing the structure of an error response that is sent back to
 * the client when an exception occurs in the application.
 */
@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {

  /**
   * The message describing the error, typically used to provide a user-friendly explanation of the
   * exception.
   */
  private String errorMessage;

  /**
   * A unique integer code representing the specific type of error, often based on {@link
   * com.ifortex.internship.exception.codes.ErrorCode}.
   */
  private int errorCode;
}
