package com.asu_cambodia.asu.Service;

import com.asu_cambodia.asu.dto.StudentDto.StudentRequest;
import com.asu_cambodia.asu.dto.StudentDto.StudentRespond;

import java.util.List;

public interface IStudentService {
    //Get All Data from data to front-end
    List<StudentRespond> findAllStudent();

    StudentRespond createStudent(StudentRequest studentRequest);
}
