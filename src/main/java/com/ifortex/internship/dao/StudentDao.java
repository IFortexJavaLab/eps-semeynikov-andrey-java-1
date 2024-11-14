package com.ifortex.internship.dao;

import com.ifortex.internship.model.enumeration.StudentField;
import com.ifortex.internship.model.Student;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Data Access Object (DAO) interface for managing {@link Student} entities. Provides methods to
 * perform CRUD operations and manage student data in the database.
 */
public interface StudentDao {

  /**
   * Creates a new student record in the database and assigns the generated id to the student
   * entity.
   *
   * <p>This method inserts the student's name into the database. The database-generated student id
   * is then set on the provided student entity.
   *
   * @param student the student entity to be created, containing at least the student's name.
   * @return the student entity.
   */
  Student create(Student student);

  /**
   * Retrieves a student by its id.
   *
   * @param id the id of the student to retrieve.
   * @return an {@link Optional} containing the student if found, or an empty Optional if not.
   */
  Optional<Student> find(long id);

  /**
   * Retrieves all students from the database.
   *
   * @return a list of all students.
   */
  List<Student> findAll();

  /**
   * Updates an existing student record in the database with specified field-value mappings.
   *
   * @param id the id of the student to update.
   * @param valuesToUpdate a map of field names and their new values for the student update.
   */
  void update(long id, Map<StudentField, Object> valuesToUpdate);

  /**
   * Deletes a student record by its id.
   *
   * @param id the id of the student to delete.
   */
  void delete(long id);
}
