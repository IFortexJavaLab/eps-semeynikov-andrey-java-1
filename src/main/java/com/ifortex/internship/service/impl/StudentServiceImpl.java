package com.ifortex.internship.service.impl;

import com.ifortex.internship.dao.StudentDao;
import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.dto.StudentUpdateDto;
import com.ifortex.internship.dto.mapper.StudentDtoToStudentMapper;
import com.ifortex.internship.dto.mapper.StudentToStudentDtoMapper;
import com.ifortex.internship.dto.markers.Create;
import com.ifortex.internship.dto.markers.Update;
import com.ifortex.internship.exception.codes.ErrorCode;
import com.ifortex.internship.exception.custom.ResourceNotFoundException;
import com.ifortex.internship.exception.custom.StudentDtoValidationException;
import com.ifortex.internship.model.Student;
import com.ifortex.internship.model.enumeration.StudentField;
import com.ifortex.internship.service.StudentService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class StudentServiceImpl implements StudentService {

  private final StudentDao studentDao;
  private final Validator validator;

  public StudentServiceImpl(StudentDao studentDao, Validator validator) {
    this.studentDao = studentDao;
    this.validator = validator;
  }

  @Override
  public StudentDto create(StudentDto studentDto) {

    Set<ConstraintViolation<StudentDto>> violations = validator.validate(studentDto, Create.class);
    if (!violations.isEmpty()) {
      throw new StudentDtoValidationException(violations);
    }

    Student student = StudentDtoToStudentMapper.convert(studentDto);
    student = studentDao.create(student);
    return StudentToStudentDtoMapper.convert(student);
  }

  @Override
  public StudentDto find(long id) {
    Student student =
        studentDao
            .find(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.STUDENT_NOT_FOUND, id));
    return StudentToStudentDtoMapper.convert(student);
  }

  @Override
  public List<StudentDto> findAll() {
    return studentDao.findAll().stream()
        .map(StudentToStudentDtoMapper::convert)
        .collect(Collectors.toList());
  }

  @Override
  public StudentDto update(long id, StudentDto studentDto) {

    Set<ConstraintViolation<StudentDto>> violations = validator.validate(studentDto, Update.class);
    if (!violations.isEmpty()) {
      throw new StudentDtoValidationException(violations);
    }

    Student student =
        studentDao
            .find(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.STUDENT_NOT_FOUND, id));

    StudentUpdateDto studentUpdateDto = new StudentUpdateDto();

    if (studentDto.getName() != null) {
      studentUpdateDto.addUpdate(StudentField.NAME, studentDto.getName());
      student.setName(studentDto.getName());
    }

    if (!studentUpdateDto.getUpdates().isEmpty())
      studentDao.update(id, studentUpdateDto.getUpdates());
    return StudentToStudentDtoMapper.convert(student);
  }

  @Override
  public void delete(long id) {
    studentDao
        .find(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.STUDENT_NOT_FOUND, id));
    studentDao.delete(id);
  }
}
