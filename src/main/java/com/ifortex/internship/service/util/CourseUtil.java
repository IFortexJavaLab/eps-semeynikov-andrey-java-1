package com.ifortex.internship.service.util;

import com.ifortex.internship.dao.util.TechnicalRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CourseUtil {
  private final TechnicalRepository repository;

  public CourseUtil(TechnicalRepository repository) {
    this.repository = repository;
  }

  @Scheduled(cron = "0 0 0 * * ?")
  public void scheduled() {
    repository.updateCourseStatus();
  }
}
