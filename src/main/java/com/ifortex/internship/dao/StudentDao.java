package com.ifortex.internship.dao;

import com.ifortex.internship.model.Student;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing {@link Student} entities. Provides methods to
 * perform CRUD operations on student data in the database.
 */
public interface StudentDao {

  /**
   * Creates a new student record in the database.
   *
   * @param student the student entity to be created.
   */
  void create(Student student);

  /**
   * Retrieves a student by its id.
   *
   * @param id the id of the student to retrieve.
   * @return an {@link Optional} containing the student if found, or empty Optional if not.
   */
  Optional<Student> find(long id);

  /**
   * Retrieves all students from the database.
   *
   * @return a list of all students.
   */
  List<Student> findAll();

  /**
   * Updates an existing student record in the database.
   *
   * @param student the student entity with updated information.
   */
  void update(Student student);

  /**
   * Deletes a student record by its id.
   *
   * @param id the id of the student to delete
   */
  void delete(long id);
}
