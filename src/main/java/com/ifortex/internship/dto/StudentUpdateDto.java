package com.ifortex.internship.dto;

import com.ifortex.internship.model.enumeration.StudentField;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class StudentUpdateDto {
  private Map<StudentField, Object> updates = new HashMap<>();

  public void addUpdate(StudentField studentField, Object value) {
    if (value != null) {
      updates.put(studentField, value);
    }
  }
}
