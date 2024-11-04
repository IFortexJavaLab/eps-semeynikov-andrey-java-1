package com.ifortex.internship.dao.impl;

import com.ifortex.internship.dao.CourseDao;
import com.ifortex.internship.model.Course;
import com.ifortex.internship.model.Student;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImpl implements CourseDao {
  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Course> courseRowMapper;
  private final RowMapper<Student> studentRowMapper;

  public CourseDaoImpl(
      JdbcTemplate jdbcTemplate,
      RowMapper<Course> courseRowMapper,
      RowMapper<Student> studentRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.courseRowMapper = courseRowMapper;
    this.studentRowMapper = studentRowMapper;
  }

  @Override
  public void create(Course course) {
    String sql =
        "INSERT INTO course (name, description, price, duration, start_date, last_update_date, course_status) VALUES (?, ?, ?, ?, ?, ?, ?::course_status)";
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
    String sql = "SELECT * FROM course WHERE id = ?";
    Optional<Course> course = jdbcTemplate.query(sql, courseRowMapper, id).stream().findFirst();

    Set<Student> students =
        Set.copyOf(
            jdbcTemplate.query(
                "SELECT s.* FROM student s JOIN course_student cs ON s.id = cs.student_id WHERE cs.course_id = ?",
                studentRowMapper,
                id));

    return course.map(
        c -> {
          c.setStudentSet(students);
          return c;
        });
  }

  @Override
  public List<Course> findAll() {
    String sql = "SELECT * FROM course";
    List<Course> courses = jdbcTemplate.query(sql, courseRowMapper);

    for (Course course : courses) {
      String studentSql =
          "SELECT s.id, s.name FROM student s "
              + "JOIN course_student cs ON s.id = cs.student_id "
              + "WHERE cs.course_id = ?";
      List<Student> students = jdbcTemplate.query(studentSql, studentRowMapper, course.getId());
      course.setStudentSet(new HashSet<>(students));
    }
    return courses;
  }

  @Override
  public void update(Course course) {
    String sql =
        "UPDATE course SET name = ?, description = ?, price = ?, duration = ?, start_date = ?, last_update_date = ?, course_status = ?::course_status  WHERE id = ?";
    jdbcTemplate.update(
        sql,
        course.getName(),
        course.getDescription(),
        course.getPrice(),
        course.getDuration(),
        course.getStartDate(),
        course.getLastUpdateDate(),
        course.getCourseStatus().name(),
        course.getId());
  }

  @Override
  public void delete(long id) {
    jdbcTemplate.update("DELETE FROM course WHERE id = ? ", id);
  }
}
