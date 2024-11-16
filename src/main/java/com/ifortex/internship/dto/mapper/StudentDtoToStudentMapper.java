package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.model.Student;

public class StudentDtoToStudentMapper {
  public static Student convert(StudentDto studentDTO) {
    return new Student().setId(studentDTO.getId()).setName(studentDTO.getName());
  }
}
