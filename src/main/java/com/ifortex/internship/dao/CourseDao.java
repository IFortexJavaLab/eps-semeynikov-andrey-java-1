package com.ifortex.internship.dao;

import com.ifortex.internship.model.enumeration.CourseField;
import com.ifortex.internship.model.Course;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing {@link Course} entities. Provides methods to
 * perform CRUD operations and manage course-student enrollments in the database.
 */
public interface CourseDao {

  /**
   * Creates a new course record in the database and assigns the generated id to the course entity.
   *
   * <p>This method inserts the course details into the database, including the name, description,
   * price, duration, start date, last update date, and course status. The course id generated by
   * the database is then set on the provided course entity, and the student list is initialized to
   * an empty set.
   *
   * @param course the course entity to be created, with details such as name, description, price,
   *     duration, start date, last update date, and course status.
   * @return the course entity.
   */
  Course create(Course course);

  /**
   * Retrieves a course by its id.
   *
   * @param id the id of the course to retrieve.
   * @return an {@link Optional} containing the course if found, or an empty Optional if not.
   */
  Optional<Course> find(long id);

  /**
   * Retrieves all courses from the database.
   *
   * @return a list of all courses.
   */
  List<Course> findAll();

  /**
   * Updates an existing course record in the database with specified field-value mappings.
   *
   * @param id the id of the course to update.
   * @param updates a map of field names and their new values for the course update.
   */
  void update(long id, Map<CourseField, Object> updates);

  /**
   * Deletes a course from the database by its id.
   *
   * @param id the id of the course to delete.
   */
  void delete(long id);

  /**
   * Enrolls a student in a course by adding an entry to the course-student association table.
   *
   * @param courseId the id of the course in which to enroll the student.
   * @param studentId the id of the student to enroll in the course.
   */
  void enrollStudentInCourse(long courseId, long studentId);

  /**
   * Removes a student from a course by deleting the corresponding entry from the course-student
   * association table.
   *
   * @param courseId the id of the course from which to remove the student.
   * @param studentId the id of the student to remove from the course.
   */
  void removeStudentFromCourse(long courseId, Long studentId);

  /**
   * Enrolls multiple students in a course using batch processing.
   *
   * @param courseId the id of the course in which to enroll students.
   * @param studentIds a list of student IDs to enroll in the course.
   */
  void batchEnrollStudents(long courseId, List<Long> studentIds);

  /**
   * Removes multiple students from a course using batch processing.
   *
   * @param courseId the id of the course from which to remove students.
   * @param studentIds a list of student id to remove from the course.
   */
  void batchRemoveStudents(long courseId, List<Long> studentIds);
}