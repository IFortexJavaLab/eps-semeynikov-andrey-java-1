package com.ifortex.internship.service.impl;

import com.ifortex.internship.dao.CourseDao;
import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.dto.CourseFilterSortDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Transactional
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

  @Transactional
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

    Map<CourseField, Object> updates = getFieldsForUpdate(courseDto);

    if (!updates.isEmpty()) {
      updates.put(CourseField.LAST_UPDATE_DATE, LocalDateTime.now());
      course.setLastUpdateDate(LocalDateTime.now());

      courseDao.update(id, updates);
    }

    CourseToCourseDtoMapper.mapNewDtoFields(course, courseDto);

    return courseDto;
  }

  @Transactional
  @Override
  public void delete(long id) {
    courseDao
        .find(id)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.STUDENT_NOT_FOUND, id));
    courseDao.delete(id);
  }

  @Transactional
  @Override
  public Set<StudentDto> enrollStudents(long courseId, List<Long> studentIds) {

    CourseDto course = find(courseId);
    if (course.getCourseStatus() != CourseStatus.OPENED) {
      throw new EnrollmentException(ErrorCode.ENROLLMENT_FAILED, "Course is not opened");
    }

    studentIds = validateStudentIds(studentIds);
    List<Long> enrolledStudentIds =
        course.getStudents().stream().map(StudentDto::getId).filter(studentIds::contains).toList();

    if (!enrolledStudentIds.isEmpty()) {
      throw new EnrollmentException(
          ErrorCode.ENROLLMENT_FAILED,
          String.format(
              "Students with these ids = %s already enrolled in the course", enrolledStudentIds));
    }

    boolean courseIsFull = course.getStudents().size() + studentIds.size() > 150;
    if (courseIsFull) {
      throw new EnrollmentLimitExceededException(ErrorCode.ENROLLMENT_LIMIT_EXCEEDED, courseId);
    }

    courseDao.batchEnrollStudents(courseId, studentIds);

    Map<CourseField, Object> updates =
        Collections.singletonMap(CourseField.LAST_UPDATE_DATE, LocalDateTime.now());
    courseDao.update(courseId, updates);

    course = find(courseId);
    return course.getStudents();
  }

  @Transactional
  @Override
  public Set<StudentDto> removeStudents(long courseId, List<Long> studentIds) {

    studentIds = validateStudentIds(studentIds);
    CourseDto course = find(courseId);
    List<Long> enrolledStudentIds = course.getStudents().stream().map(StudentDto::getId).toList();

    List<Long> notEnrolledStudentIds =
        studentIds.stream().filter(id -> !enrolledStudentIds.contains(id)).toList();

    if (!notEnrolledStudentIds.isEmpty()) {
      throw new EnrollmentException(
          ErrorCode.ENROLLMENT_FAILED,
          String.format(
              "Students with these ids = %s aren't enrolled in the course", notEnrolledStudentIds));
    }

    courseDao.batchRemoveStudents(courseId, studentIds);

    Map<CourseField, Object> updates =
        Collections.singletonMap(CourseField.LAST_UPDATE_DATE, LocalDateTime.now());
    courseDao.update(courseId, updates);

    course = find(courseId);
    return course.getStudents();
  }

  private List<Long> validateStudentIds(List<Long> studentIds) {

    if (studentIds == null || studentIds.isEmpty()) {
      throw new EnrollmentException(
          ErrorCode.ENROLLMENT_FAILED, "List of the student's ids is empty");
    }

    boolean hasNegativeId = studentIds.stream().anyMatch(id -> id <= 0);
    if (hasNegativeId) {
      throw new EnrollmentException(
          ErrorCode.ENROLLMENT_FAILED, "List contains invalid student ids");
    }

    studentIds = studentIds.stream().distinct().toList();

    List<Long> missingStudentIds = studentService.findNonexistentStudentIds(studentIds);
    if (!missingStudentIds.isEmpty()) {
      throw new EnrollmentException(
          ErrorCode.ENROLLMENT_FAILED,
          String.format("Students with these ids = %s do not exist", missingStudentIds));
    }
    return studentIds;
  }

  @Override
  public List<CourseDto> findWithParametersAndSort(CourseFilterSortDto courseFilterSortDto) {
    return courseDao.findWithParametersAndSort(courseFilterSortDto).stream()
        .map(CourseToCourseDtoMapper::convert)
        .toList();
  }

  private Map<CourseField, Object> getFieldsForUpdate(CourseDto courseDto) {
    Map<CourseField, Object> fields = new HashMap<>();
    if (courseDto.getName() != null) {
      fields.put(CourseField.NAME, courseDto.getName());
    }
    if (courseDto.getDescription() != null) {
      fields.put(CourseField.DESCRIPTION, courseDto.getDescription());
    }
    if (courseDto.getPrice() != null) {
      fields.put(CourseField.PRICE, courseDto.getPrice());
    }
    if (courseDto.getDuration() != null) {
      fields.put(CourseField.DURATION, courseDto.getDuration());
    }
    if (courseDto.getStartDate() != null) {
      fields.put(CourseField.START_DATE, courseDto.getStartDate());
    }
    if (courseDto.getCourseStatus() != null) {
      fields.put(CourseField.COURSE_STATUS, courseDto.getCourseStatus().name());
    }

    return fields;
  }
}
