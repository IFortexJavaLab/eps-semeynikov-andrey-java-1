package com.ifortex.internship.controller;

import com.ifortex.internship.dto.CourseDto;
import com.ifortex.internship.dto.CourseFilterSortDto;
import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.dto.enumeration.SortType;
import com.ifortex.internship.service.CourseService;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  // region Post
  @PostMapping
  public ResponseEntity<CourseDto> create(@RequestBody CourseDto courseDto) {
    courseDto = courseService.create(courseDto);
    return new ResponseEntity<>(courseDto, HttpStatus.CREATED);
  }

  @PostMapping("/{courseId}/students")
  public ResponseEntity<Set<StudentDto>> enrollStudents(
      @PathVariable("courseId") long courseId, @RequestBody List<Long> studentsIds) {
    Set<StudentDto> enrolledStudents = courseService.enrollStudents(courseId, studentsIds);
    return new ResponseEntity<>(enrolledStudents, HttpStatus.CREATED);
  }

  // endregion

  // region Get
  @GetMapping("/{id}")
  public ResponseEntity<CourseDto> find(@PathVariable("id") long id) {
    CourseDto foundCourse = courseService.find(id);
    return ResponseEntity.ok(foundCourse);
  }

  @GetMapping("/search")
  public ResponseEntity<List<CourseDto>> searchWithParameters(
      @RequestParam(name = "studentName", required = false) String studentName,
      @RequestParam(name = "courseName", required = false) String courseName,
      @RequestParam(name = "courseDesc", required = false) String courseDescription,
      @RequestParam(name = "sortByDate", required = false) SortType sortByDate,
      @RequestParam(name = "sortByName", required = false) SortType sortByName) {

    CourseFilterSortDto courseFilterSortDto =
        new CourseFilterSortDto()
            .setStudentName(studentName)
            .setCourseName(courseName)
            .setCourseDescription(courseDescription)
            .setSortByDate(sortByDate)
            .setSortByName(sortByName);

    return ResponseEntity.ok(courseService.findWithParametersAndSort(courseFilterSortDto));
  }

  // endregion

  // region Put
  @PutMapping("/{id}")
  public ResponseEntity<CourseDto> update(
      @PathVariable("id") int id, @RequestBody CourseDto courseDTO) {
    CourseDto courseDto = courseService.update(id, courseDTO);
    return ResponseEntity.ok(courseDto);
  }

  // endregion

  // region Delete
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable("id") long id) {
    courseService.delete(id);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{courseId}/students")
  public ResponseEntity<Set<StudentDto>> removeStudents(
      @PathVariable("courseId") long courseId, @RequestBody List<Long> studentsIds) {
    Set<StudentDto> enrolledStudents = courseService.removeStudents(courseId, studentsIds);
    return new ResponseEntity<>(enrolledStudents, HttpStatus.CREATED);
  }
  // endregion
}
