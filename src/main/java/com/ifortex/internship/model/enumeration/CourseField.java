package com.ifortex.internship.model.enumeration;

import lombok.Getter;

@Getter
public enum CourseField {

  ID("id"),
  NAME("name"),
  DESCRIPTION("description"),
  PRICE("price"),
  DURATION("duration"),
  START_DATE("startDate"),
  LAST_UPDATE_DATE("lastUpdateDate"),
  COURSE_STATUS("courseStatus");

  private final String fieldName;

  CourseField(String fieldName) {
    this.fieldName = fieldName;
  }
}
