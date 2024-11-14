package com.ifortex.internship.service;

import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.exception.custom.ResourceNotFoundException;
import com.ifortex.internship.exception.custom.StudentDtoValidationException;

import java.util.List;

/**
 * Service interface for managing student operations.
 */
public interface StudentService {

  /**
   * Creates a new student.
   *
   * @param studentDTO the student data transfer object containing the student's details
   * @return the created student's data transfer object
   */
  StudentDto create(StudentDto studentDTO);

  /**
   * Finds a student by their unique ID.
   *
   * @param id the ID of the student to find
   * @return the student's data transfer object if found
   * @throws ResourceNotFoundException if no student is found with the provided ID
   */
  StudentDto find(long id);

  /**
   * Retrieves all students.
   *
   * @return a list of data transfer objects representing all students
   */
  List<StudentDto> findAll();

  /**
   * Updates the details of an existing student.
   *
   * @param id the ID of the student to update
   * @param studentDto the student data transfer object with updated details
   * @return the updated student's data transfer object
   * @throws ResourceNotFoundException if no student is found with the provided ID
   * @throws StudentDtoValidationException if validation fails for the provided student data
   */
  StudentDto update(long id, StudentDto studentDto);

  /**
   * Deletes a student by their unique ID.
   *
   * @param id the ID of the student to delete
   * @throws ResourceNotFoundException if no student is found with the provided ID
   */
  void delete(long id);
}
