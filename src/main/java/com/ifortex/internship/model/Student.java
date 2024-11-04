package com.ifortex.internship.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Student {
  private long id;
  private String name;

  @Override
  public boolean equals(Object object) {
    if (this == object) return true;
    if (object == null || getClass() != object.getClass()) return false;

    Student student = (Student) object;
    return id == student.id && name.equals(student.name);
  }

  @Override
  public int hashCode() {
    int result = Long.hashCode(id);
    result = 31 * result + name.hashCode();
    return result;
  }
}
