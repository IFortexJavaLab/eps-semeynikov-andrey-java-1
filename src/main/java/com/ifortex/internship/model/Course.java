package com.ifortex.internship.model;

import com.ifortex.internship.model.enumiration.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
  int id;
  String name;
  String description;
  double price;
  int duration;
  LocalDate startDate;
  LocalDateTime lastUpdatedDate;
  CourseStatus courseStatus;
}
