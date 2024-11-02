package com.ifortex.internship.DAO;

import com.ifortex.internship.model.Course;
import java.util.List;
import java.util.Optional;

public interface CourseDAO {
  void create(Course course);

  Optional<Course> find(long id);

  List<Course> findAll();

  void update(Course course);

  void delete(long id);
}
