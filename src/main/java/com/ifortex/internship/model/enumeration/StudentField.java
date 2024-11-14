package com.ifortex.internship.model.enumeration;

import lombok.Getter;

@Getter
public enum StudentField {
  ID("id"),
  NAME("name");
  private final String fieldName;

  StudentField(String fieldName) {
    this.fieldName = fieldName;
  }
}
