package com.ifortex.internship.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ifortex.internship.dto.markers.Create;
import com.ifortex.internship.dto.markers.Update;
import com.ifortex.internship.model.enumeration.CourseStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CourseDto {

  private long id;

  @NotNull(message = "Name can't be null", groups = Create.class)
  @Size(
      min = 2,
      max = 50,
      message = "Name must be between 2 and 50 characters long",
      groups = {Update.class, Create.class})
  private String name;

  @NotNull(message = "Description can't be null", groups = Create.class)
  @Size(
      max = 300,
      message = "Description must be at most 300 characters",
      groups = {Create.class, Update.class})
  private String description;

  @NotNull(message = "Price can't be null", groups = Create.class)
  @DecimalMin(
      value = "0.0",
      inclusive = false,
      message = "Price must be greater than zero",
      groups = {Create.class, Update.class})
  @Digits(
      integer = 10,
      fraction = 2,
      message = "Price must be a valid amount with up to 2 decimal places",
      groups = {Create.class, Update.class})
  private BigDecimal price;

  @NotNull(message = "Duration can't be null", groups = Create.class)
  @Min(
      value = 1,
      message = "Duration must be at least 1",
      groups = {Create.class, Update.class})
  @Max(
      value = 365,
      message = "Duration must be less than 365 days",
      groups = {Create.class, Update.class})
  private Integer duration;

  @NotNull(message = "Start date is required", groups = Create.class)
  @FutureOrPresent(
      message = "Start date must be in the future or present",
      groups = {Create.class, Update.class})
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime lastUpdateDate;

  @NotNull(message = "Course status is required", groups = Create.class)
  private CourseStatus courseStatus;

  private Set<StudentDto> students;
}
