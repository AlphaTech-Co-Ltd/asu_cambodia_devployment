package com.asu_cambodia.asu.dto.StudentDto;

import com.asu_cambodia.asu.enumStirng.Gender;
import com.asu_cambodia.asu.enumStirng.RoleUser;
import com.asu_cambodia.asu.model.Student;

import java.time.format.DateTimeFormatter;

public record StudentRespond(
        Long id,
        String firstName,
        String lastName,
        String imageStudentUrl,
        Gender gender,
        String email,
        String phoneNumber,
        RoleUser role,
        String createdDate,
        String updatedDate
) {
    public static StudentRespond converterStudentMapping(Student student){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return new StudentRespond(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getImageStudentUrl(),
                student.getGender(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getRoleUser(),
                student.getCreatedDate() != null ? student.getCreatedDate().format(dateTimeFormatter) : null,
                student.getUpdatedDate() != null ? student.getUpdatedDate().format(dateTimeFormatter) : null
        );
    }
}
