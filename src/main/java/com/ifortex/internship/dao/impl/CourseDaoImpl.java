package com.ifortex.internship.dao.impl;

import com.ifortex.internship.dao.CourseDao;
import com.ifortex.internship.dao.mapper.CourseWithStudentsExtractor;
import com.ifortex.internship.model.Course;
import com.ifortex.internship.model.enumeration.CourseField;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImpl implements CourseDao {
  private final JdbcTemplate jdbcTemplate;
  private final CourseWithStudentsExtractor courseWithStudentsExtractor;

  public CourseDaoImpl(
      JdbcTemplate jdbcTemplate, CourseWithStudentsExtractor courseWithStudentsExtractor) {
    this.jdbcTemplate = jdbcTemplate;
    this.courseWithStudentsExtractor = courseWithStudentsExtractor;
  }

  @Override
  public Course create(Course course) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql =
        "INSERT INTO course (name, description, price, duration, start_date, last_update_date, course_status)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
          ps.setString(1, course.getName());
          ps.setString(2, course.getDescription());
          ps.setBigDecimal(3, course.getPrice());
          ps.setInt(4, course.getDuration());
          ps.setObject(5, course.getStartDate());
          ps.setObject(6, course.getLastUpdateDate());
          ps.setString(7, course.getCourseStatus().name());
          return ps;
        },
        keyHolder);
    course.setId(keyHolder.getKey().longValue());
    course.setStudents(Collections.emptySet());
    return course;
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
            + "LEFT JOIN course_student cs ON c.id = cs.course_id "
            + "LEFT JOIN student s ON cs.student_id = s.id";

    return jdbcTemplate.query(sql, courseWithStudentsExtractor);
  }

  @Override
  public void update(long id, Map<CourseField, Object> updates) {
    List<String> setClauses = new ArrayList<>();
    List<Object> params = new ArrayList<>();

    updates.forEach(
        (field, value) -> {
          setClauses.add(field + " = ?");
          params.add(value);
        });

    String sql = "UPDATE course SET " + String.join(", ", setClauses) + " WHERE id = ?";
    params.add(id);

    jdbcTemplate.update(sql, params.toArray());
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
