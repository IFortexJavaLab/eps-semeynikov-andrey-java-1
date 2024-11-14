package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.model.Course;

public class CourseDtoToCourseMapper {
  public static Course convert(CourseDto courseDto) {
    Course course = new Course();
    course.setId(courseDto.getId());
    course.setName(courseDto.getName());
    course.setDescription(courseDto.getDescription());
    course.setPrice(courseDto.getPrice());
    course.setDuration(courseDto.getDuration());
    course.setStartDate(courseDto.getStartDate());
    course.setLastUpdateDate(courseDto.getLastUpdateDate());
    course.setCourseStatus(courseDto.getCourseStatus());
    return course;
  }
}
