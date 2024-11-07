package com.ifortex.internship.exception;

public class ResourceNotFoundException extends RuntimeException {
  private final int errorCode;

  public ResourceNotFoundException(String resourceName, Object resourceId) {
    super(
        String.format(
            "Requested resource not found (type = %s, id = %s)", resourceName, resourceId));
    this.errorCode = generateErrorCode(resourceName);
  }

  public int getErrorCode() {
    return errorCode;
  }

  private int generateErrorCode(String resourceName) {
    switch (resourceName.toLowerCase()) {
      case "course":
        return 40401;
      case "student":
        return 40402;
      default:
        return 40400;
    }
  }
}
