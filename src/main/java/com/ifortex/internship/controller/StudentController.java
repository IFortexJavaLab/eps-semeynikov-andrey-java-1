package com.ifortex.internship.controller;

import com.ifortex.internship.dto.StudentDto;
import com.ifortex.internship.service.StudentService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {
  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @PostMapping
  public ResponseEntity<StudentDto> create(@RequestBody StudentDto studentDto) {
    StudentDto createdStudent = studentService.create(studentDto);
    return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentDto> find(@PathVariable("id") long id) {
    StudentDto foundStudent = studentService.find(id);
    return ResponseEntity.ok(foundStudent);
  }

  @GetMapping
  public ResponseEntity<List<StudentDto>> findAll() {
    return ResponseEntity.ok(studentService.findAll());
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudentDto> update(
      @PathVariable("id") long id, @RequestBody StudentDto studentDto) {
    StudentDto updatedStudent = studentService.update(id, studentDto);
    return ResponseEntity.ok(updatedStudent);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") long id) {
    studentService.delete(id);
    return ResponseEntity.ok().build();
  }
}
