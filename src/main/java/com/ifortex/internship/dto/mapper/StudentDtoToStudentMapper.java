package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentDtoToStudentMapper {
  public static Student convert(StudentDto studentDTO) {
    Student student = new Student();
    student.setId(studentDTO.getId());
    student.setName(studentDTO.getName());
    return student;
  }
}
