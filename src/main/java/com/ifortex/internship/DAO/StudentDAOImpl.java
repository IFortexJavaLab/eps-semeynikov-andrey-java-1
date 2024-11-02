package com.ifortex.internship.DAO;

import com.ifortex.internship.DAO.mapper.StudentRowMapper;
import com.ifortex.internship.model.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAOImpl implements StudentDAO {
  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Student> studentRowMapper = new StudentRowMapper();

  public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
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
  public void update(Student student) {
    String sql = "UPDATE student SET name = ? WHERE id = ?";
    jdbcTemplate.update(sql, student.getName(), student.getId());
  }

  @Override
  public void delete(long id) {
    String sql = "DELETE FROM student WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }
}
