package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.model.Course;
import java.util.Collections;
import java.util.Optional;

public class CourseToCourseDtoMapper {
  public static CourseDto convert(Course course) {
    CourseDto courseDto =
        new CourseDto()
            .setId(course.getId())
            .setName(course.getName())
            .setDescription(course.getDescription())
            .setPrice(course.getPrice())
            .setDuration(course.getDuration())
            .setStartDate(course.getStartDate())
            .setLastUpdateDate(course.getLastUpdateDate())
            .setCourseStatus(course.getCourseStatus());
    courseDto.setStudents(
        StudentToStudentDtoMapper.convert(
            Optional.ofNullable(course.getStudents()).orElse(Collections.emptySet())));
    return courseDto;
  }
}
