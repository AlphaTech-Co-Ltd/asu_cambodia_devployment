package com.asu_cambodia.asu.dto.UserDto;

import com.asu_cambodia.asu.enumStirng.Gender;
import com.asu_cambodia.asu.enumStirng.RoleUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public record UserRequest(
    @NotBlank(message = "First name is required")
    String firstName,
    @NotBlank(message = "Last name is required")
    String lastName,
    MultipartFile imageUrl,
    @NotNull(message = "Gender is required")
    Gender gender,
    @NotBlank(message = "Phone number is required")
    @Size(min = 8, max = 15, message = "Phone number must be between 8 and 15 digits")
    String phoneNumber,
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,
    @NotBlank(message = "Username is required")
    String username,
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    String password,
    @NotBlank(message = "Confirm password is required")
    String confirmPassword,
    @NotNull(message = "Role is required")
    RoleUser roleUser,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
