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
    Student student =
        jdbcTemplate.query(
            sql, new Object[] {id}, rs -> rs.next() ? studentRowMapper.mapRow(rs, 1) : null);

    return Optional.ofNullable(student);
  }

  @Override
  public List<Student> findAll() {
    String sql = "SELECT * FROM student";
    return jdbcTemplate.query(sql, studentRowMapper);
  }

  @Override
  public void update(long id, Student student) {
    String sql = "UPDATE student SET name = ? WHERE id = ?";
    jdbcTemplate.update(sql, student.getName(), id);
  }

  @Override
  public void delete(long id) {
    jdbcTemplate.update("DELETE FROM student WHERE id = ?", id);
  }
}
