package com.ifortex.internship.dao;

import com.ifortex.internship.model.Course;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing {@link Course} entities. Provides methods to
 * perform CRUD operations on course data in the database.
 */
public interface CourseDao {

  /**
   * Creates a new course record in the database.
   *
   * @param course the course entity to be created.
   */
  void create(Course course);

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
   * Updates an existing course record in the database.
   *
   * @param course the course entity with updated information.
   */
  void update(Course course);

  /**
   * Deletes a course from the database by its id.
   *
   * @param id the id of the course to delete.
   */
  void delete(long id);
}
