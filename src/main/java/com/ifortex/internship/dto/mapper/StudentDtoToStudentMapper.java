package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.model.Student;

import java.util.Set;
import java.util.stream.Collectors;

public class StudentDtoToStudentMapper {
  public static Student convert(StudentDto studentDTO) {
    return new Student().setId(studentDTO.getId()).setName(studentDTO.getName());
  }

  public static Set<Student> convert(Set<StudentDto> students) {
    return students.stream().map(StudentDtoToStudentMapper::convert).collect(Collectors.toSet());
  }
}
