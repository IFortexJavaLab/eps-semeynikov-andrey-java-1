package com.ifortex.internship.dao.impl;

import com.ifortex.internship.dao.StudentDao;
import com.ifortex.internship.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
  public void create(Student student) {
    String sql = "INSERT INTO student (name) VALUES (?)";
    jdbcTemplate.update(sql, student.getName());
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
  public void update(long id, Map<String, Object> updates) {

    StringBuilder sql = new StringBuilder("UPDATE student SET ");
    List<Object> params = new ArrayList<>();

    updates.forEach(
        (field, value) -> {
          sql.append(field).append(" = ?, ");
          params.add(value);
        });

    sql.setLength(sql.length() - 2);
    sql.append(" WHERE id = ?");
    params.add(id);

    jdbcTemplate.update(sql.toString(), params.toArray());
  }

  @Override
  public void delete(long id) {
    String sql = "DELETE FROM student WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }
}
