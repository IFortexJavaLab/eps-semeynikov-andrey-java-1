package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.model.Student;

public class StudentToStudentDtoMapper {
  public static StudentDto convert(Student student) {
    return new StudentDto().setId(student.getId()).setName(student.getName());
  }
}
