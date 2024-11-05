package com.ifortex.internship.dao.impl;

import com.ifortex.internship.dao.CourseDao;
import com.ifortex.internship.dao.mapper.CourseWithStudentsExtractor;
import com.ifortex.internship.model.Course;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImpl implements CourseDao {
  private final JdbcTemplate jdbcTemplate;
  private final ResultSetExtractor<List<Course>> courseWithStudentsExtractor;

  public CourseDaoImpl(
      JdbcTemplate jdbcTemplate, CourseWithStudentsExtractor courseWithStudentsExtractor) {
    this.jdbcTemplate = jdbcTemplate;
    this.courseWithStudentsExtractor = courseWithStudentsExtractor;
  }

  @Override
  public void create(Course course) {
    String sql =
        "INSERT INTO course (name, description, price, duration, start_date, last_update_date, course_status)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?::course_status)";
    jdbcTemplate.update(
        sql,
        course.getName(),
        course.getDescription(),
        course.getPrice(),
        course.getDuration(),
        course.getStartDate(),
        course.getLastUpdateDate(),
        course.getCourseStatus().name());
  }

  @Override
  public Optional<Course> find(long id) {
    String sql =
        "SELECT c.id AS course_id, "
            + "c.name AS course_name, "
            + "c.description AS course_description, "
            + "c.price AS course_price, "
            + "c.duration AS course_duration, "
            + "c.start_date AS course_start_date, "
            + "c.last_update_date AS course_last_update_date, "
            + "c.course_status AS course_status, "
            + "s.id AS student_id, "
            + "s.name AS student_name "
            + "FROM course c "
            + "LEFT JOIN course_student cs ON c.id = cs.course_id "
            + "LEFT JOIN student s ON cs.student_id = s.id "
            + "WHERE c.id = ?";

    List<Course> courses = jdbcTemplate.query(sql, courseWithStudentsExtractor, id);
    return courses.stream().findFirst();
  }

  @Override
  public List<Course> findAll() {
    String sql =
        "SELECT c.id AS course_id, "
            + "c.name AS course_name, "
            + "c.description AS course_description, "
            + "c.price AS course_price, "
            + "c.duration AS course_duration, "
            + "c.start_date AS course_start_date, "
            + "c.last_update_date AS course_last_update_date, "
            + "c.course_status AS course_status, "
            + "s.id AS student_id, "
            + "s.name AS student_name "
            + "FROM course c "
            + "JOIN course_student cs ON c.id = cs.course_id "
            + "JOIN student s ON cs.student_id = s.id";

    return jdbcTemplate.query(sql, courseWithStudentsExtractor);
  }

  @Override
  public void update(long id, Map<String, Object> updates) {
    StringBuilder sql = new StringBuilder("UPDATE course SET ");
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
    String sql = "DELETE FROM course WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }

  @Override
  public void enrollStudentInCourse(long courseId, long studentId) {
    String sql = "INSERT INTO course_student (course_id, student_id) VALUES (?, ?)";
    jdbcTemplate.update(sql, courseId, studentId);
  }

  @Override
  public void removeStudentFromCourse(long courseId, Long studentId) {
    String sql = "DELETE FROM course_student WHERE course_id = ? AND student_id = ?";
    jdbcTemplate.update(sql, courseId, studentId);
  }

  @Override
  public void batchEnrollStudents(long courseId, List<Long> studentIds) {
    String sql = "INSERT INTO course_student (course_id, student_id) VALUES (?, ?)";
    jdbcTemplate.batchUpdate(
        sql,
        studentIds,
        studentIds.size(),
        (ps, studentId) -> {
          ps.setLong(1, courseId);
          ps.setLong(2, studentId);
        });
  }

  @Override
  public void batchRemoveStudents(long courseId, List<Long> studentIds) {
    String sql = "DELETE FROM course_student WHERE course_id = ? AND student_id = ?";
    jdbcTemplate.batchUpdate(
        sql,
        studentIds,
        studentIds.size(),
        (ps, studentId) -> {
          ps.setLong(1, courseId);
          ps.setLong(2, studentId);
        });
  }
}
