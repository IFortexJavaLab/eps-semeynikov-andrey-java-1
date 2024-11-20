package com.ifortex.internship.model;

import com.ifortex.internship.model.enumeration.CourseStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Course {

  @EqualsAndHashCode.Exclude private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int duration;
  private LocalDateTime startDate;
  private LocalDateTime lastUpdateDate;
  private CourseStatus courseStatus;
  private Set<Student> students;
}
