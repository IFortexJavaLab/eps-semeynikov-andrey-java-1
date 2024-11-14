package com.ifortex.internship.service;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.dto.CourseStudentUpdateDto;
import com.ifortex.internship.exception.custom.EnrollmentException;
import com.ifortex.internship.exception.custom.EnrollmentLimitExceededException;
import com.ifortex.internship.exception.custom.ResourceNotFoundException;

import java.util.List;

/**
 * Service interface for handling operations related to courses. Provides methods for creating,
 * updating, finding, and deleting courses, as well as enrolling and removing students from a
 * course.
 */
public interface CourseService {

  /**
   * Creates a new course in the db.
   *
   * @param courseDto the course data transfer object containing details of the course to create
   * @return the created course as a {@link CourseDto} with generated ID and other details
   */
  CourseDto create(CourseDto courseDto);

  /**
   * Retrieves the details of a specific course by its ID.
   *
   * @param id the unique identifier of the course to find
   * @return the found course as a CourseDto
   * @throws ResourceNotFoundException if the course is not found
   */
  CourseDto find(long id);

  /**
   * Retrieves all courses in the db.
   *
   * @return a list of CourseDto representing all courses
   */
  List<CourseDto> findAll();

  /**
   * Updates the details of a specific course.
   *
   * @param id the unique identifier of the course to update
   * @param courseDto the updated course data transfer object with new details
   * @return the updated course as a CourseDto
   * @throws ResourceNotFoundException if the course is not found
   */
  CourseDto update(long id, CourseDto courseDto);

  /**
   * Deletes a specific course by its ID.
   *
   * @param id the unique identifier of the course to delete
   * @throws ResourceNotFoundException if the course is not found
   */
  void delete(long id);

  /**
   * Enrolls a list of students in a specific course.
   *
   * @param courseId the unique identifier of the course in which to enroll students
   * @param studentIds a list of student IDs to enroll in the course
   * @return a {@link CourseStudentUpdateDto} representing the updated enrollment status
   * @throws EnrollmentException if enrollment fails due to constraints
   * @throws EnrollmentLimitExceededException if the course enrollment limit is exceeded
   */
  CourseStudentUpdateDto enrollStudents(long courseId, List<Long> studentIds);

  /**
   * Removes a list of students from a specific course.
   *
   * @param courseId the unique identifier of the course from which to remove students
   * @param studentIds a list of student IDs to remove from the course
   * @return a CourseStudentUpdateDto representing the updated status after removal
   * @throws EnrollmentException if removal fails due to constraints
   */
  CourseStudentUpdateDto removeStudents(long courseId, List<Long> studentIds);
}
