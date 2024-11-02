package com.ifortex.internship.DAO;

import com.ifortex.internship.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentDAO {
  void create(Student student);

  Optional<Student> find(long id);

  List<Student> findAll();

  void update(Student student);

  void delete(long id);
}
