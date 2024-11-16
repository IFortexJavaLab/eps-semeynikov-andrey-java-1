package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.model.Course;
import java.util.stream.Collectors;

public class CourseToCourseDtoMapper {
  public static CourseDto convert(Course course) {
    return new CourseDto()
        .setId(course.getId())
        .setName(course.getName())
        .setDescription(course.getDescription())
        .setPrice(course.getPrice())
        .setDuration(course.getDuration())
        .setStartDate(course.getStartDate())
        .setCourseStatus(course.getCourseStatus())
        .setStudents(
            course.getStudents().stream()
                .map(StudentToStudentDtoMapper::convert)
                .collect(Collectors.toSet()));
  }
}
