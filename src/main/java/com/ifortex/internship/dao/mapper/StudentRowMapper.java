package com.ifortex.internship.dao.mapper;

import com.ifortex.internship.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentRowMapper implements RowMapper<Student> {
  @Override
  public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
    Student student = new Student();
    student.setId(rs.getLong("id"));
    student.setName(rs.getString("name"));
    return student;
  }
}
