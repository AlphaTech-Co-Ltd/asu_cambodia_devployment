package com.asu_cambodia.asu.Service.impl;

import com.asu_cambodia.asu.Service.IStudentService;
import com.asu_cambodia.asu.dto.StudentDto.StudentRequest;
import com.asu_cambodia.asu.dto.StudentDto.StudentRespond;
import com.asu_cambodia.asu.excption.customException.ResourceNotFoundException;
import com.asu_cambodia.asu.model.Student;
import com.asu_cambodia.asu.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentRespond> findAllStudent() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("Student not found");
        }
        return students.stream()
                .map(StudentRespond::converterStudentMapping)
                .collect(Collectors.toList());
    }

    @Override
    public StudentRespond createStudent(StudentRequest studentRequest) {
        if(studentRepository.existsByEmail(studentRequest.email())){
            throw new IllegalArgumentException("Email already exists");
        }
        Student student = Student.builder()
                .firstName(studentRequest.firstName())
                .lastName(studentRequest.lastName())
                .imageStudentUrl(studentRequest.imageStudentUrl().toString())
                .gender(studentRequest.gender())
                .email(studentRequest.email())
                .phoneNumber(studentRequest.phoneNumber())
                .roleUser(studentRequest.role())
                .createdDate(LocalDateTime.now())
                .build();
        Student savedStudent = studentRepository.save(student);
        return StudentRespond.converterStudentMapping(savedStudent);
    }
}
