package com.ifortex.internship.model;

import com.ifortex.internship.model.enumeration.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
  private int id;
  private String name;
  private String description;
  private double price;
  private int duration;
  private LocalDate startDate;
  private LocalDateTime lastUpdatedDate;
  private CourseStatus courseStatus;
  private Set<Student> studentSet;
}
