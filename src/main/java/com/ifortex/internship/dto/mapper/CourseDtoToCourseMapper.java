package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.model.Course;
import java.util.stream.Collectors;

public class CourseDtoToCourseMapper {
  public static Course convert(CourseDto courseDto) {
    return new Course()
        .setId(courseDto.getId())
        .setName(courseDto.getName())
        .setDescription(courseDto.getDescription())
        .setPrice(courseDto.getPrice())
        .setDuration(courseDto.getDuration())
        .setStartDate(courseDto.getStartDate())
        .setCourseStatus(courseDto.getCourseStatus())
        .setStudents(
            courseDto.getStudents().stream()
                .map(StudentDtoToStudentMapper::convert)
                .collect(Collectors.toSet()));
  }
}
