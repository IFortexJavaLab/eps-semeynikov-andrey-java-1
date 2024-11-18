package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.model.Course;
import java.util.Collections;
import java.util.Optional;

public class CourseDtoToCourseMapper {
  public static Course convert(CourseDto courseDto) {
    Course course =
        new Course()
            .setId(courseDto.getId())
            .setName(courseDto.getName())
            .setDescription(courseDto.getDescription())
            .setPrice(courseDto.getPrice())
            .setDuration(courseDto.getDuration())
            .setStartDate(courseDto.getStartDate())
            .setLastUpdateDate(courseDto.getLastUpdateDate())
            .setCourseStatus(courseDto.getCourseStatus());

    course.setStudents(
        StudentDtoToStudentMapper.convert(
            Optional.ofNullable(courseDto.getStudents()).orElse(Collections.emptySet())));
    return course;
  }
}
