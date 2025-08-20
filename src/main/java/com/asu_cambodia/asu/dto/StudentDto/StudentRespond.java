package com.asu_cambodia.asu.dto.StudentDto;

import com.asu_cambodia.asu.enumStirng.Gender;
import com.asu_cambodia.asu.model.Student;

import java.time.format.DateTimeFormatter;

public record StudentRespond(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Gender gender,
        String address,
        String city,
        String state,
        String country,
        String createdDate,
        String updatedDate
) {
    public static StudentRespond converterStudentMapping(Student student){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return new StudentRespond(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getGender(),
                student.getAddress(),
                student.getCity(),
                student.getState(),
                student.getCountry(),
                student.getCreatedDate() != null ? student.getCreatedDate().format(dateTimeFormatter) : null,
                student.getUpdatedDate() != null ? student.getUpdatedDate().format(dateTimeFormatter) : null
        );
    }
}
