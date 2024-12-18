package com.ifortex.internship.dao.mapper;

import com.ifortex.internship.model.Course;
import com.ifortex.internship.model.enumeration.CourseStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourseRowMapper implements RowMapper<Course> {
  @Override
  public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
    Course course = new Course();
    course.setId(rs.getLong("id"));
    course.setName(rs.getString("name"));
    course.setDescription(rs.getString("description"));
    course.setPrice(rs.getBigDecimal("price"));
    course.setDuration(rs.getInt("duration"));
    course.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());
    course.setLastUpdateDate(rs.getTimestamp("last_update_date").toLocalDateTime());
    course.setCourseStatus(CourseStatus.valueOf(rs.getString("course_status")));
    return course;
  }
}
