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

import com.ifortex.internship.dao.impl.CourseDaoImpl;
import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.dto.markers.Create;
import com.ifortex.internship.dto.markers.Update;
import com.ifortex.internship.exception.custom.CourseDtoValidationException;
import com.ifortex.internship.exception.custom.EnrollmentException;
import com.ifortex.internship.exception.custom.ResourceNotFoundException;
import com.ifortex.internship.model.Course;
import com.ifortex.internship.model.Student;
import com.ifortex.internship.model.enumeration.CourseStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
class CourseServiceImplTest {

  @Mock private CourseDaoImpl courseDao;

  @Mock private StudentServiceImpl studentService;

  @Mock private Validator validator;

  @InjectMocks private CourseServiceImpl courseService;

  @Test
  void testCreateCourse_Success() {
    CourseDto courseDto = new CourseDto();
    courseDto
        .setName("Java")
        .setDescription("Learn Java")
        .setPrice(BigDecimal.valueOf(199.99))
        .setDuration(30)
        .setStartDate(LocalDateTime.now().plusDays(1))
        .setCourseStatus(CourseStatus.OPENED);

    Course course = new Course();
    course.setName(courseDto.getName());
    course.setId(1L);

    when(validator.validate(courseDto, Create.class)).thenReturn(Collections.emptySet());
    when(courseDao.create(any(Course.class))).thenReturn(course);

    CourseDto result = courseService.create(courseDto);

    assertNotNull(result);
    assertEquals(course.getName(), result.getName());
    verify(courseDao).create(any(Course.class));
  }

  @Test
  void testCreateCourse_ValidationError() {
    CourseDto courseDto = new CourseDto();
    ConstraintViolation<CourseDto> violation = mock(ConstraintViolation.class);
    Set<ConstraintViolation<CourseDto>> violations = Set.of(violation);

    when(validator.validate(courseDto, Create.class)).thenReturn(violations);

    assertThrows(CourseDtoValidationException.class, () -> courseService.create(courseDto));
    verify(courseDao, never()).create(any(Course.class));
  }

  @Test
  void testFindCourseById_Success() {
    long courseId = 1L;
    Course course = new Course();
    course.setId(courseId);
    course.setName("Java Course");

    when(courseDao.find(courseId)).thenReturn(Optional.of(course));

    CourseDto result = courseService.find(courseId);

    assertNotNull(result);
    assertEquals(course.getName(), result.getName());
    verify(courseDao).find(courseId);
  }

  @Test
  void testFindCourseById_NotFound() {
    long courseId = 1L;
    when(courseDao.find(courseId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> courseService.find(courseId));
    verify(courseDao).find(courseId);
  }

  @Test
  void testFindAllCourses_Success() {
    Course course1 = new Course();
    course1.setId(1L);
    course1.setName("Java Course");

    Course course2 = new Course();
    course2.setId(2L);
    course2.setName("Python Course");

    List<Course> courses = List.of(course1, course2);
    when(courseDao.findAll()).thenReturn(courses);

    List<CourseDto> result = courseService.findAll();

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Java Course", result.get(0).getName());
    assertEquals("Python Course", result.get(1).getName());
    verify(courseDao).findAll();
  }

  @Test
  void testUpdateCourse_Success() {
    long courseId = 1L;
    CourseDto courseDto = new CourseDto();
    courseDto.setName("Advanced Java Course");

    Course course = new Course();
    course.setId(courseId);
    course.setName("Java Course");
    course.setStudents(Collections.emptySet());

    when(validator.validate(courseDto, Update.class)).thenReturn(Collections.emptySet());
    when(courseDao.find(courseId)).thenReturn(Optional.of(course));

    CourseDto result = courseService.update(courseId, courseDto);

    assertNotNull(result);
    assertEquals(courseDto.getName(), result.getName());
    verify(courseDao).find(courseId);
    verify(courseDao).update(eq(courseId), anyMap());
  }

  @Test
  void testUpdateCourse_ValidationError() {
    long courseId = 1L;
    CourseDto courseDto = new CourseDto();
    courseDto.setName("");

    ConstraintViolation<CourseDto> violation = mock(ConstraintViolation.class);
    Set<ConstraintViolation<CourseDto>> violations = Set.of(violation);

    when(validator.validate(courseDto, Update.class)).thenReturn(violations);

    assertThrows(
        CourseDtoValidationException.class, () -> courseService.update(courseId, courseDto));
    verify(courseDao, never()).update(anyLong(), anyMap());
  }

  @Test
  void testUpdateCourse_NotFound() {
    long courseId = 1L;
    CourseDto courseDto = new CourseDto();
    courseDto.setName("Advanced Java Course");

    when(validator.validate(courseDto, Update.class)).thenReturn(Collections.emptySet());
    when(courseDao.find(courseId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> courseService.update(courseId, courseDto));
    verify(courseDao).find(courseId);
    verify(courseDao, never()).update(anyLong(), anyMap());
  }

  @Test
  void testDeleteCourse_Success() {
    long courseId = 1L;
    Course course = new Course();
    course.setId(courseId);

    when(courseDao.find(courseId)).thenReturn(Optional.of(course));

    courseService.delete(courseId);

    verify(courseDao).find(courseId);
    verify(courseDao).delete(courseId);
  }

  @Test
  void testDeleteCourse_NotFound() {
    long courseId = 1L;

    when(courseDao.find(courseId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> courseService.delete(courseId));
    verify(courseDao).find(courseId);
    verify(courseDao, never()).delete(courseId);
  }

  @Test
  void testEnrollStudents_Success() {
    long courseId = 1L;
    List<Long> studentIds = List.of(1L, 2L);

    Course course = new Course();
    course
        .setId(1L)
        .setName("Java Course")
        .setCourseStatus(CourseStatus.OPENED)
        .setStudents(Collections.emptySet());

    when(courseDao.find(courseId)).thenReturn(Optional.of(course));
    when(studentService.findNonexistentStudentIds(studentIds)).thenReturn(Collections.emptyList());
    when(courseDao.find(courseId)).thenReturn(Optional.of(course));

    Set<StudentDto> result = courseService.enrollStudents(courseId, studentIds);

    assertNotNull(result);
    verify(courseDao).batchEnrollStudents(eq(courseId), eq(studentIds));
  }

  @Test
  void testEnrollStudents_CourseClosed() {
    long courseId = 1L;
    List<Long> studentIds = List.of(1L, 2L);

    Course course = new Course();
    course
        .setId(1L)
        .setName("Java Course")
        .setCourseStatus(CourseStatus.CLOSED)
        .setStudents(Collections.emptySet());

    when(courseDao.find(courseId)).thenReturn(Optional.of(course));

    assertThrows(
        EnrollmentException.class, () -> courseService.enrollStudents(courseId, studentIds));
  }

  @Test
  void testEnrollStudents_StudentAlreadyEnrolled() {
    long courseId = 1L;
    List<Long> studentIds = List.of(1L, 2L);

    Course course =
        new Course()
            .setId(courseId)
            .setCourseStatus(CourseStatus.OPENED)
            .setStudents(Set.of(new Student().setId(1L).setName("John")));
    when(courseDao.find(courseId)).thenReturn(Optional.of(course));

    assertThrows(
        EnrollmentException.class, () -> courseService.enrollStudents(courseId, studentIds));
  }

  @Test
  void testEnrollStudents_StudentNotFound() {
    long courseId = 1L;
    List<Long> studentIds = List.of(1L, 2L);

    Course course = new Course().setId(courseId).setCourseStatus(CourseStatus.OPENED);
    when(courseDao.find(courseId)).thenReturn(Optional.of(course));
    when(studentService.findNonexistentStudentIds(studentIds)).thenReturn(List.of(2L));

    assertThrows(
        EnrollmentException.class, () -> courseService.enrollStudents(courseId, studentIds));
  }

  @Test
  void testEnrollStudents_CourseNotFound() {
    long courseId = 1L;
    List<Long> studentIds = List.of(1L, 2L);

    when(courseDao.find(courseId)).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class, () -> courseService.enrollStudents(courseId, studentIds));
  }

  @Test
  void testRemoveStudents_Success() {
    long courseId = 1L;
    List<Long> studentIds = List.of(1L, 2L);

    Course course =
        new Course()
            .setId(courseId)
            .setCourseStatus(CourseStatus.OPENED)
            .setStudents(
                Set.of(
                    new Student().setId(1L).setName("John"),
                    new Student().setId(2L).setName("Doe")));
    when(courseDao.find(courseId)).thenReturn(Optional.of(course));
    when(studentService.findNonexistentStudentIds(studentIds)).thenReturn(Collections.emptyList());

    Set<StudentDto> result = courseService.removeStudents(courseId, studentIds);

    assertNotNull(result);
    verify(courseDao).batchRemoveStudents(eq(courseId), eq(studentIds));
  }

  @Test
  void testRemoveStudents_StudentNotEnrolled() {
    long courseId = 1L;
    List<Long> studentIds = List.of(1L, 3L);

    Course course =
        new Course()
            .setId(courseId)
            .setCourseStatus(CourseStatus.OPENED)
            .setStudents(Collections.emptySet());
    when(courseDao.find(courseId)).thenReturn(Optional.of(course));

    assertThrows(
        EnrollmentException.class, () -> courseService.removeStudents(courseId, studentIds));
  }

  @Test
  void testRemoveStudents_CourseNotFound() {
    long courseId = 1L;
    List<Long> studentIds = List.of(1L, 2L);

    when(courseDao.find(courseId)).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class, () -> courseService.removeStudents(courseId, studentIds));
  }
}
