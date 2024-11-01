package com.ifortex.internship.model;

import com.ifortex.internship.model.enumeration.CourseStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
  private long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int duration;
  private LocalDateTime startDate;
  private LocalDateTime lastUpdatedDate;
  private CourseStatus courseStatus;
  private Set<Student> studentSet;
}
