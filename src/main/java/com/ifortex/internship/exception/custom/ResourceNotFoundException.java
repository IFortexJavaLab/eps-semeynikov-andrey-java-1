package com.ifortex.internship.exception.custom;

import com.ifortex.internship.exception.codes.ErrorCode;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
  private final ErrorCode errorCode;
  private final long resourceId;

  public ResourceNotFoundException(ErrorCode errorCode, long resourceId) {
    super(String.format("%s (id = %d)", errorCode.getDefaultMessage(), resourceId));
    this.errorCode = errorCode;
    this.resourceId = resourceId;
  }
}
