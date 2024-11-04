package com.ifortex.internship.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

  @JsonProperty("startDate")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime startDate;

  @JsonProperty("lastUpdateDate")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime lastUpdateDate;

  private CourseStatus courseStatus;
  private Set<Student> students;
}
