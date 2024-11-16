package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.model.Student;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentToStudentDtoMapper {
  public static StudentDto convert(Student student) {
    return new StudentDto().setId(student.getId()).setName(student.getName());
  }

  public static Set<StudentDto> convert(Set<Student> students) {
    return students.stream().map(StudentToStudentDtoMapper::convert).collect(Collectors.toSet());
  }
}
