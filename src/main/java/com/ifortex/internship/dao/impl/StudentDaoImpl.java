package com.ifortex.internship.dao.impl;

import com.ifortex.internship.dao.StudentDao;
import com.ifortex.internship.model.enumeration.StudentField;
import com.ifortex.internship.model.Student;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDaoImpl implements StudentDao {
  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Student> studentRowMapper;

  public StudentDaoImpl(JdbcTemplate jdbcTemplate, RowMapper<Student> studentRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.studentRowMapper = studentRowMapper;
  }

  @Override
  public Student create(Student student) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO student (name) VALUES (?)";
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
          ps.setString(1, student.getName());
          return ps;
        },
        keyHolder);
    student.setId(keyHolder.getKey().longValue());
    return student;
  }

  @Override
  public Optional<Student> find(long id) {
    String sql = "SELECT * FROM student WHERE id = ?";
    return jdbcTemplate.query(sql, studentRowMapper, id).stream().findFirst();
  }

  @Override
  public List<Student> findAll() {
    String sql = "SELECT * FROM student";
    return jdbcTemplate.query(sql, studentRowMapper);
  }

  @Override
  public void update(long id, Map<StudentField, Object> updates) {
    List<String> setClauses = new ArrayList<>();
    List<Object> params = new ArrayList<>();

    updates.forEach(
        (field, value) -> {
          setClauses.add(field + " = ?");
          params.add(value);
        });

    String sql = "UPDATE student SET " + String.join(", ", setClauses) + " WHERE id = ?";
    params.add(id);

    jdbcTemplate.update(sql, params.toArray());
  }

  @Override
  public void delete(long id) {
    String sql = "DELETE FROM student WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }
}
