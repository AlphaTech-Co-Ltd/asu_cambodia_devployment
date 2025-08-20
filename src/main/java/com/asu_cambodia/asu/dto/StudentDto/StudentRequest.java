package com.asu_cambodia.asu.dto.StudentDto;

import com.asu_cambodia.asu.enumStirng.Gender;

public record StudentRequest (
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
){
}
