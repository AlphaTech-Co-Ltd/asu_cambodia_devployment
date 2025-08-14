package com.asu_cambodia.asu.dto.UserDto;

import com.asu_cambodia.asu.enumStirng.Gender;
import com.asu_cambodia.asu.enumStirng.RoleUser;
import com.asu_cambodia.asu.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record UserRespond (
        Long id,
        String firstName,
        String lastName,
        String imageUrl,
        Gender gender,
        String phoneNumber,
        String email,
        String username,
        RoleUser roleUser,
        String createdAt,
        String updatedAt
){
    public static UserRespond converterUserMapping(User user){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return new UserRespond(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getImageUrl(),
                user.getGender(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.getCreatedAt() != null ? user.getCreatedAt().format(dateTimeFormatter) : null,
                user.getUpdatedAt() != null ? user.getUpdatedAt().format(dateTimeFormatter) : null
        );
    }
}
