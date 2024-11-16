package com.ifortex.internship.service.impl;

import com.ifortex.internship.dao.CourseDao;
import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.dto.CourseStudentUpdateDto;
import com.ifortex.internship.dto.CourseUpdateDto;
import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.dto.mapper.CourseDtoToCourseMapper;
import com.ifortex.internship.dto.mapper.CourseToCourseDtoMapper;
import com.ifortex.internship.dto.markers.Create;
import com.ifortex.internship.dto.markers.Update;
import com.ifortex.internship.exception.codes.ErrorCode;
import com.ifortex.internship.exception.custom.CourseDtoValidationException;
import com.ifortex.internship.exception.custom.EnrollmentException;
import com.ifortex.internship.exception.custom.EnrollmentLimitExceededException;
import com.ifortex.internship.exception.custom.ResourceNotFoundException;
import com.ifortex.internship.model.Course;
import com.ifortex.internship.model.enumeration.CourseField;
import com.ifortex.internship.model.enumeration.CourseStatus;
import com.ifortex.internship.service.CourseService;
import com.ifortex.internship.service.StudentService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CourseServiceImpl implements CourseService {
  private final StudentService studentService;
  private final CourseDao courseDao;
  private final Validator validator;

  public CourseServiceImpl(
      StudentService studentService, CourseDao courseDao, Validator validator) {
    this.studentService = studentService;
    this.courseDao = courseDao;
    this.validator = validator;
  }

  @Override
  public CourseDto create(CourseDto courseDto) {

    Set<ConstraintViolation<CourseDto>> violations = validator.validate(courseDto, Create.class);
    if (!violations.isEmpty()) {
      throw new CourseDtoValidationException(violations);
    }

    Course course = CourseDtoToCourseMapper.convert(courseDto);
    course.setLastUpdateDate(LocalDateTime.now());
    course = courseDao.create(course);
    return CourseToCourseDtoMapper.convert(course);
  }

  @Override
  public CourseDto find(long id) {
    Course course =
        courseDao
            .find(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.COURSE_NOT_FOUND, id));
    return CourseToCourseDtoMapper.convert(course);
  }

  @Override
  public List<CourseDto> findAll() {
    return courseDao.findAll().stream()
        .map(CourseToCourseDtoMapper::convert)
        .collect(Collectors.toList());
  }

  @Override
  public CourseDto update(long id, CourseDto courseDto) {

    Set<ConstraintViolation<CourseDto>> violations = validator.validate(courseDto, Update.class);
    if (!violations.isEmpty()) {
      throw new CourseDtoValidationException(violations);
    }

    Course course =
        courseDao
            .find(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.COURSE_NOT_FOUND, id));

    CourseUpdateDto courseUpdateDto = new CourseUpdateDto();

    if (courseDto.getName() != null) {
      courseUpdateDto.addUpdate(CourseField.NAME, courseDto.getName());
      course.setName(courseDto.getName());
    }
    if (courseDto.getDescription() != null) {
      courseUpdateDto.addUpdate(CourseField.DESCRIPTION, courseDto.getDescription());
      course.setDescription(courseDto.getDescription());
    }
    if (courseDto.getPrice() != null) {
      courseUpdateDto.addUpdate(CourseField.PRICE, courseDto.getPrice());
      course.setPrice(courseDto.getPrice());
    }
    if (courseDto.getDuration() != null) {
      courseUpdateDto.addUpdate(CourseField.DURATION, courseDto.getDuration());
      course.setDuration(courseDto.getDuration());
    }
    if (courseDto.getStartDate() != null) {
      courseUpdateDto.addUpdate(CourseField.START_DATE, courseDto.getStartDate());
      course.setStartDate(courseDto.getStartDate());
    }
    if (courseDto.getCourseStatus() != null) {
      courseUpdateDto.addUpdate(CourseField.COURSE_STATUS, courseDto.getCourseStatus().name());
      course.setCourseStatus(courseDto.getCourseStatus());
    }

    if (!courseUpdateDto.getUpdates().isEmpty()) {
      courseUpdateDto.addUpdate(CourseField.LAST_UPDATE_DATE, LocalDateTime.now());
      course.setLastUpdateDate(LocalDateTime.now());

      courseDao.update(id, courseUpdateDto.getUpdates());
    }
    return CourseToCourseDtoMapper.convert(course);
  }

  @Override
  public void delete(long id) {
    courseDao
        .find(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.STUDENT_NOT_FOUND, id));
    courseDao.delete(id);
  }

  @Override
  public CourseStudentUpdateDto enrollStudents(long courseId, List<Long> studentIds) {

    CourseDto course = find(courseId);

    if (course.getCourseStatus() != CourseStatus.OPENED)
      throw new EnrollmentException(ErrorCode.ENROLLMENT_FAILED, "Course is not opened");

    if (studentIds == null || studentIds.isEmpty()) {
      throw new EnrollmentException(
          ErrorCode.ENROLLMENT_FAILED, "List of the student's ids is empty");
    }

    if (studentIds.stream().anyMatch(id -> id == null || id <= 0)) {
      throw new EnrollmentException(
          ErrorCode.ENROLLMENT_FAILED, "List contains invalid student ids");
    }

    Set<Long> uniqueIds = new HashSet<>(studentIds);
    if (uniqueIds.size() != studentIds.size()) {
      throw new EnrollmentException(
          ErrorCode.ENROLLMENT_FAILED, "List contains duplicate student ids");
    }

    List<StudentDto> students = studentIds.stream().map(studentService::find).toList();

    students.stream()
        .filter(course.getStudents()::contains)
        .findFirst()
        .ifPresent(
            student -> {
              throw new EnrollmentException(
                  ErrorCode.ENROLLMENT_FAILED,
                  String.format(
                      "Student with id = %d is enrolled on the course with id = %d",
                      student.getId(), courseId));
            });

    if (course.getStudents().size() + students.size() <= 150) {
      courseDao.batchEnrollStudents(courseId, studentIds);

      Map<CourseField, Object> updates =
          Collections.singletonMap(CourseField.LAST_UPDATE_DATE, LocalDateTime.now());
      courseDao.update(courseId, updates);
    } else {
      throw new EnrollmentLimitExceededException(ErrorCode.ENROLLMENT_LIMIT_EXCEEDED, courseId);
    }

    return new CourseStudentUpdateDto(courseId, studentIds);
  }

  @Override
  public CourseStudentUpdateDto removeStudents(long courseId, List<Long> studentIds) {

    if (studentIds == null || studentIds.isEmpty()) {
      throw new EnrollmentException(
          ErrorCode.DELETION_FROM_COURSE_FAILED, "List of the student's ids is empty");
    }

    if (studentIds.stream().anyMatch(id -> id == null || id <= 0)) {
      throw new EnrollmentException(
          ErrorCode.DELETION_FROM_COURSE_FAILED, "List contains invalid student ids");
    }

    Set<Long> uniqueIds = new HashSet<>(studentIds);
    if (uniqueIds.size() != studentIds.size()) {
      throw new EnrollmentException(
          ErrorCode.DELETION_FROM_COURSE_FAILED, "List contains duplicate student ids");
    }

    CourseDto course = find(courseId);
    List<StudentDto> students = studentIds.stream().map(studentService::find).toList();

    students.stream()
        .filter(student -> !course.getStudents().contains(student))
        .findFirst()
        .ifPresent(
            student -> {
              throw new EnrollmentException(
                  ErrorCode.DELETION_FROM_COURSE_FAILED,
                  String.format(
                      "Student with id = %d isn't enrolled on the course with id = %d",
                      student.getId(), courseId));
            });

    courseDao.batchRemoveStudents(courseId, studentIds);

    Map<CourseField, Object> updates =
        Collections.singletonMap(CourseField.LAST_UPDATE_DATE, LocalDateTime.now());
    courseDao.update(courseId, updates);

    return new CourseStudentUpdateDto(courseId, studentIds);
  }
}
