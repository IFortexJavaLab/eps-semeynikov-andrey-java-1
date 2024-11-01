package com.ifortex.internship.DAO;

import com.ifortex.internship.DAO.mapper.CourseRowMapper;
import com.ifortex.internship.DAO.mapper.StudentRowMapper;
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
public class CourseDAOImpl implements CourseDAO {
  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<Course> courseRowMapper = new CourseRowMapper();
  private final RowMapper<Student> studentRowMapper = new StudentRowMapper();

  public CourseDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
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
        course.getLastUpdatedDate(),
        course.getCourseStatus().name());
  }

  @Override
  public Optional<Course> find(long id) {
    String sql = "SELECT * FROM course WHERE id = ?";
    Course course =
        jdbcTemplate.query(
            sql, new Object[] {id}, rs -> rs.next() ? courseRowMapper.mapRow(rs, 1) : null);

    Set<Student> students =
        Set.copyOf(
            jdbcTemplate.query(
                "SELECT s.* FROM student s JOIN course_student cs ON s.id = cs.student_id WHERE cs.course_id = ?",
                studentRowMapper,
                id));
    if (course != null) {
      course.setStudentSet(students);
      return Optional.of(course);
    }
    return Optional.empty();
  }

  @Override
  public List<Course> findAll() {
    String sql = "SELECT * FROM course";
    List<Course> courses = jdbcTemplate.query(sql, new CourseRowMapper());

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
  public void update(long id, Course course) {
    String sql =
        "UPDATE course SET name = ?, description = ?, price = ?, duration = ?, start_date = ?, last_update_date = ?, course_status = ?::course_status  WHERE id = ?";
    jdbcTemplate.update(
        sql,
        course.getName(),
        course.getDescription(),
        course.getPrice(),
        course.getDuration(),
        course.getStartDate(),
        course.getLastUpdatedDate(),
        course.getCourseStatus().name(),
        id);
  }

  @Override
  public void delete(long id) {
    jdbcTemplate.update("DELETE FROM course WHERE id = ? ", id);
  }
}
