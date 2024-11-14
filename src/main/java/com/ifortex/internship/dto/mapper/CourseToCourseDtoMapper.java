package com.ifortex.internship.dto.mapper;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.model.Course;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CourseToCourseDtoMapper {
  public static CourseDto convert(Course course) {
    CourseDto courseDTO = new CourseDto();
    courseDTO.setId(course.getId());
    courseDTO.setName(course.getName());
    courseDTO.setDescription(course.getDescription());
    courseDTO.setPrice(course.getPrice());
    courseDTO.setDuration(course.getDuration());
    courseDTO.setStartDate(course.getStartDate());
    courseDTO.setLastUpdateDate(course.getLastUpdateDate());
    courseDTO.setCourseStatus(course.getCourseStatus());
    courseDTO.setStudents(
        course.getStudents().stream()
            .map(StudentToStudentDtoMapper::convert)
            .collect(Collectors.toSet()));
    return courseDTO;
  }
}
