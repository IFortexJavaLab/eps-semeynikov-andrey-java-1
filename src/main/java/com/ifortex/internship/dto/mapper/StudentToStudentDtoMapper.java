package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentToStudentDtoMapper {
  public static StudentDto convert(Student student) {
    StudentDto studentDTO = new StudentDto();
    studentDTO.setId(student.getId());
    studentDTO.setName(student.getName());
    return studentDTO;
  }
}
