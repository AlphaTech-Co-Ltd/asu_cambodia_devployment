package com.asu_cambodia.asu.dto.StudentDto;

import com.asu_cambodia.asu.enumStirng.Gender;
import com.asu_cambodia.asu.enumStirng.RoleUser;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record StudentRequest (
        String firstName,
        String lastName,
        MultipartFile imageStudentUrl,
        Gender gender,
        String email,
        String phoneNumber,
        RoleUser role,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
){
}
