package com.asu_cambodia.asu.controller;

import com.asu_cambodia.asu.Service.impl.StudentService;
import com.asu_cambodia.asu.dto.StudentDto.StudentRequest;
import com.asu_cambodia.asu.dto.StudentDto.StudentRespond;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/student")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentRespond>> getAllStudents() {
        List<StudentRespond> studentResponds = studentService.findAllStudent();
        return ResponseEntity.status(HttpStatus.OK).body(studentResponds);
    }

    @PostMapping
    public ResponseEntity<StudentRespond> createStudent(@RequestBody StudentRequest studentRequest) {
        StudentRespond studentRespond = studentService.createStudent(studentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(studentRespond);
    }
}
