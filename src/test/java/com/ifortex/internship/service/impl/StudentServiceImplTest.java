package com.ifortex.internship.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ifortex.internship.dao.impl.StudentDaoImpl;
import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.dto.markers.Create;
import com.ifortex.internship.dto.markers.Update;
import com.ifortex.internship.exception.custom.ResourceNotFoundException;
import com.ifortex.internship.exception.custom.StudentDtoValidationException;
import com.ifortex.internship.model.Student;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

  @Mock private StudentDaoImpl studentDao;

  @Mock private Validator validator;

  @InjectMocks private StudentServiceImpl studentService;

  @Test
  void testCreateStudent_Success() {
    StudentDto studentDto = new StudentDto();
    studentDto.setName("John Doe");

    Student student = new Student();
    student.setName("John Doe");
    student.setId(1L);

    when(validator.validate(studentDto, Create.class)).thenReturn(Collections.emptySet());
    when(studentDao.create(any(Student.class))).thenReturn(student);

    StudentDto result = studentService.create(studentDto);

    assertNotNull(result);
    assertEquals(student.getName(), result.getName());
    verify(validator).validate(studentDto, Create.class);
    verify(studentDao).create(any(Student.class));
  }

  @Test
  void testCreateStudent_ValidationError() {
    StudentDto studentDto = new StudentDto();
    ConstraintViolation<StudentDto> violation = mock(ConstraintViolation.class);
    Set<ConstraintViolation<StudentDto>> violations = Set.of(violation);

    when(validator.validate(studentDto, Create.class)).thenReturn(violations);

    assertThrows(StudentDtoValidationException.class, () -> studentService.create(studentDto));
    verify(validator).validate(studentDto, Create.class);
    verify(studentDao, never()).create(any(Student.class));
  }

  @Test
  void testFindStudentById_Success() {
    long studentId = 1L;
    Student student = new Student();
    student.setId(studentId);
    student.setName("John Doe");

    when(studentDao.find(studentId)).thenReturn(Optional.of(student));

    StudentDto result = studentService.find(studentId);

    assertNotNull(result);
    assertEquals(student.getName(), result.getName());
    verify(studentDao).find(studentId);
  }

  @Test
  void testFindStudentById_NotFound() {
    long studentId = 1L;
    when(studentDao.find(studentId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> studentService.find(studentId));
    verify(studentDao).find(studentId);
  }

  @Test
  void testFindAllStudents_Success() {
    Student student1 = new Student();
    student1.setId(1L);
    student1.setName("John Doe");

    Student student2 = new Student();
    student2.setId(2L);
    student2.setName("Jane Doe");

    List<Student> students = List.of(student1, student2);
    when(studentDao.findAll()).thenReturn(students);

    List<StudentDto> result = studentService.findAll();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("John Doe", result.get(0).getName());
    assertEquals("Jane Doe", result.get(1).getName());
    verify(studentDao).findAll();
  }

  @Test
  void testUpdateStudent_Success() {
    long studentId = 1L;
    StudentDto studentDto = new StudentDto();
    studentDto.setName("Jane Doe");

    Student student = new Student();
    student.setId(studentId);
    student.setName("John Doe");

    when(validator.validate(studentDto, Update.class)).thenReturn(Collections.emptySet());
    when(studentDao.find(studentId)).thenReturn(Optional.of(student));

    StudentDto result = studentService.update(studentId, studentDto);

    assertNotNull(result);
    assertEquals(studentDto.getName(), result.getName());
    verify(studentDao).find(studentId);
    verify(studentDao).update(eq(studentId), anyMap());
  }

  @Test
  void testUpdateStudent_ValidationError() {
    long studentId = 1L;
    StudentDto studentDto = new StudentDto();
    studentDto.setName("Jane Doe");

    ConstraintViolation<StudentDto> violation = mock(ConstraintViolation.class);
    Set<ConstraintViolation<StudentDto>> violations = Set.of(violation);

    when(validator.validate(studentDto, Update.class)).thenReturn(violations);

    assertThrows(
        StudentDtoValidationException.class, () -> studentService.update(studentId, studentDto));
    verify(validator).validate(studentDto, Update.class);
    verify(studentDao, never()).update(anyLong(), anyMap());
  }

  @Test
  void testUpdateStudent_NotFound() {
    long studentId = 1L;
    StudentDto studentDto = new StudentDto();
    studentDto.setName("Jane Doe");

    when(validator.validate(studentDto, Update.class)).thenReturn(Collections.emptySet());
    when(studentDao.find(studentId)).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class, () -> studentService.update(studentId, studentDto));
    verify(studentDao).find(studentId);
    verify(studentDao, never()).update(anyLong(), anyMap());
  }
}
