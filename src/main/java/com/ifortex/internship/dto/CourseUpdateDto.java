package com.ifortex.internship.dto;

import com.ifortex.internship.model.enumeration.CourseField;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CourseUpdateDto {
  private Map<CourseField, Object> updates = new HashMap<>();

    public void addUpdate(CourseField courseField, Object value) {
    if (value != null) {
      updates.put(courseField, value);
    }
  }
}
