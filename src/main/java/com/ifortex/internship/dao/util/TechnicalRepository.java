package com.ifortex.internship.dao.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TechnicalRepository {

  private final JdbcTemplate jdbcTemplate;

  public TechnicalRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void updateCourseStatus() {
    String sql = "CALL update_today_courses_status()";
    jdbcTemplate.update(sql);
  }
}
