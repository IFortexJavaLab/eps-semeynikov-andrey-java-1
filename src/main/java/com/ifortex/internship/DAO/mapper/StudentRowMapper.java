package com.ifortex.internship.DAO.mapper;

import com.ifortex.internship.model.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class StudentRowMapper implements RowMapper<Student> {
  @Override
  public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
    Student student = new Student();
    student.setId(rs.getLong("id"));
    student.setName(rs.getString("name"));
    return student;
  }
}
