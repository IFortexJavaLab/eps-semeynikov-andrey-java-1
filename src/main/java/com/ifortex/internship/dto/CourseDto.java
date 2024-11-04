package com.ifortex.internship.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {
  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("duration")
  private int duration;

  @JsonProperty("startDate")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime startDate;

  @JsonProperty("lastUpdateDate")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime lastUpdateDate;

  @JsonProperty("courseStatus")
  private CourseStatus courseStatus;

  @JsonProperty("students")
  private Set<StudentDto> students;
}
