package com.ifortex.internship.dao.mapper;

import com.ifortex.internship.model.Course;
import com.ifortex.internship.model.Student;
import com.ifortex.internship.model.enumeration.CourseStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class CourseWithStudentsExtractor implements ResultSetExtractor<List<Course>> {

  @Override
  public List<Course> extractData(ResultSet rs) throws SQLException {
    Map<Long, Course> courseMap = new LinkedHashMap<>();

    while (rs.next()) {
      long courseId = rs.getLong("course_id");
      Course course = courseMap.get(courseId);

      if (course == null) {
        course = new Course();
        course.setId(courseId);
        course.setName(rs.getString("course_name"));
        course.setDescription(rs.getString("course_description"));
        course.setPrice(rs.getBigDecimal("course_price"));
        course.setDuration(rs.getInt("course_duration"));
        course.setStartDate(rs.getTimestamp("course_start_date").toLocalDateTime());
        course.setLastUpdateDate(rs.getTimestamp("course_last_update_date").toLocalDateTime());
        course.setCourseStatus(CourseStatus.valueOf(rs.getString("course_status")));
        course.setStudents(new HashSet<>());
        courseMap.put(courseId, course);
      }

      long studentId = rs.getLong("student_id");
      if (studentId > 0) {
        Student student = new Student();
        student.setId(studentId);
        student.setName(rs.getString("student_name"));
        course.getStudents().add(student);
      }
    }

    return new ArrayList<>(courseMap.values());
  }
}
