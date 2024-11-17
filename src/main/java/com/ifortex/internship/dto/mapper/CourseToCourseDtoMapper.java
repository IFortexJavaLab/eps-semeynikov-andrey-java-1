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

  public static void mapNewDtoFields(Course oldCourseEntity, CourseDto courseDto) {
    if (courseDto.getName() == null) {
      courseDto.setName(oldCourseEntity.getName());
    }
    if (courseDto.getDescription() == null) {
      courseDto.setDescription(oldCourseEntity.getDescription());
    }
    if (courseDto.getPrice() == null) {
      courseDto.setPrice(oldCourseEntity.getPrice());
    }
    if (courseDto.getStartDate() == null) {
      courseDto.setStartDate(oldCourseEntity.getStartDate());
    }
    if (courseDto.getDuration() == null) {
      courseDto.setDuration(oldCourseEntity.getDuration());
    }
    if (courseDto.getCourseStatus() == null) {
      courseDto.setCourseStatus(oldCourseEntity.getCourseStatus());
    }
    if (courseDto.getStudents() == null) {
      courseDto.setStudents(StudentToStudentDtoMapper.convert(oldCourseEntity.getStudents()));
    }
    courseDto.setId(oldCourseEntity.getId());
    courseDto.setLastUpdateDate(oldCourseEntity.getLastUpdateDate());
  }
}
