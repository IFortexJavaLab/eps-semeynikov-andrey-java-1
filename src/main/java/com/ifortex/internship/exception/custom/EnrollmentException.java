package com.ifortex.internship.exception.custom;

import com.ifortex.internship.exception.codes.ErrorCode;
import lombok.Getter;

@Getter
public class EnrollmentException extends RuntimeException{
    private final ErrorCode errorCode;

    public EnrollmentException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }
}
