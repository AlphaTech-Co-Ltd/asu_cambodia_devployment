package com.asu_cambodia.asu.Service.impl;

import com.asu_cambodia.asu.Service.IUserService;
import com.asu_cambodia.asu.config.jwt.JwtService;
import com.asu_cambodia.asu.config.utils.FileUploadingConfig;
import com.asu_cambodia.asu.dto.AuthResponse;
import com.asu_cambodia.asu.dto.UserDto.UserCheckerAccountLogIn;
import com.asu_cambodia.asu.dto.UserDto.UserRequest;
import com.asu_cambodia.asu.dto.UserDto.UserRespond;
import com.asu_cambodia.asu.excption.customException.ResourceNotFoundException;
import com.asu_cambodia.asu.model.User;
import com.asu_cambodia.asu.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public List<UserRespond> ReadAllUsersData() {
        List<User> getAllUsers = userRepository.findAll();
        if (getAllUsers.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return getAllUsers.stream()
                .map(UserRespond::converterUserMapping)
                .collect(Collectors.toList());
    }

    @Override
    public UserRespond GetUserById(Long id) {
        User userFindById = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserRespond.converterUserMapping(userFindById);
    }

    @Override
    public UserRespond updateUserPassword(Long id, String currentPassword, String newPassword) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Verify old password
        if (!bCryptPasswordEncoder.matches(currentPassword, existingUser.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        // Validate new password
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("New password must not be empty");
        }

        // Save new password
        existingUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        existingUser.setUpdatedAt(LocalDateTime.now());
        userRepository.save(existingUser);

        return UserRespond.converterUserMapping(existingUser);
    }


    @Override
    public UserRespond createUser(UserRequest userRequest) throws IOException {
        if (!userRequest.password().equals(userRequest.confirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        if (userRepository.existsByEmailIgnoreCase(userRequest.email())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.existsByFirstNameAndLastNameAndUsername(
                userRequest.firstName(),
                userRequest.lastName(),
                userRequest.username()
        )) {
            throw new IllegalArgumentException("Username already exists for this name");
        }
        User userInsert = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .imageUrl(SaveImage(userRequest.imageUrl()))
                .gender(userRequest.gender())
                .phoneNumber(userRequest.phoneNumber())
                .email(userRequest.email())
                .username(userRequest.username())
                .password(bCryptPasswordEncoder.encode(userRequest.password()))
                .role(userRequest.roleUser())
                .createdAt(LocalDateTime.now())
                .build();
        User savedUser = userRepository.save(userInsert);
        return UserRespond.converterUserMapping(savedUser);
    }

    @Override
    public UserRespond updateUser(Long id, UserRequest userRequest) throws IOException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean updated = false;

        // Update password only if provided
        if (userRequest.password() != null && !userRequest.password().isBlank()) {
            if (!userRequest.password().equals(userRequest.confirmPassword())) {
                throw new IllegalArgumentException("Passwords do not match");
            }
            existingUser.setPassword(bCryptPasswordEncoder.encode(userRequest.password()));
            updated = true;
        }

        // Email update only if provided and changed
        if (userRequest.email() != null && !userRequest.email().isBlank()
                && !existingUser.getEmail().equalsIgnoreCase(userRequest.email())) {
            if (userRepository.existsByEmailIgnoreCase(userRequest.email())) {
                throw new IllegalArgumentException("Email already exists");
            }
            existingUser.setEmail(userRequest.email());
            updated = true;
        }

        // Username update only if provided and changed
        if (userRequest.username() != null && !userRequest.username().isBlank()
                && !existingUser.getUsername().equalsIgnoreCase(userRequest.username())) {
            if (userRepository.existsByUsername(userRequest.username())) {
                throw new IllegalArgumentException("Username already exists");
            }
            existingUser.setUsername(userRequest.username());
            updated = true;
        }

        // Update image if provided
        if (userRequest.imageUrl() != null && !userRequest.imageUrl().isEmpty()) {
            existingUser.setImageUrl(SaveImage(userRequest.imageUrl()));
            updated = true;
        }

        // First name
        if (userRequest.firstName() != null && !userRequest.firstName().isBlank()
                && !userRequest.firstName().equals(existingUser.getFirstName())) {
            existingUser.setFirstName(userRequest.firstName());
            updated = true;
        }

        // Last name
        if (userRequest.lastName() != null && !userRequest.lastName().isBlank()
                && !userRequest.lastName().equals(existingUser.getLastName())) {
            existingUser.setLastName(userRequest.lastName());
            updated = true;
        }

        // Gender
        if (userRequest.gender() != null && !userRequest.gender().equals(existingUser.getGender())) {
            existingUser.setGender(userRequest.gender());
            updated = true;
        }

        // Phone number
        if (userRequest.phoneNumber() != null && !userRequest.phoneNumber().isBlank()
                && !userRequest.phoneNumber().equals(existingUser.getPhoneNumber())) {
            existingUser.setPhoneNumber(userRequest.phoneNumber());
            updated = true;
        }

        // Role
        if (userRequest.roleUser() != null && !userRequest.roleUser().equals(existingUser.getRole())) {
            existingUser.setRole(userRequest.roleUser());
            updated = true;
        }

        // Save only if something changed
        if (updated) {
            existingUser.setUpdatedAt(LocalDateTime.now());
            userRepository.save(existingUser);
        }

        return UserRespond.converterUserMapping(existingUser);
    }


    @Override
    public void deleteUser(Long id) throws IOException {
        User findUserById = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (findUserById.getImageUrl() != null && !findUserById.getImageUrl().isEmpty()) {
            final String imageUrl = "upload/Userprofile";
            Path pathfile = Paths.get(imageUrl).resolve(findUserById.getImageUrl());
            if (Files.exists(pathfile)) {
                Files.delete(pathfile);
            }
        }
        userRepository.deleteById(id);
    }

    public AuthResponse CheckAuth(UserCheckerAccountLogIn userCheckerAccountLogIn) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userCheckerAccountLogIn.username(),
                        userCheckerAccountLogIn.password()
                )
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(userCheckerAccountLogIn.username());

            // Fetch user from DB
            User user = userRepository.findByUsername(userCheckerAccountLogIn.username())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Convert User entity to UserRespond DTO
            UserRespond userRespond = UserRespond.converterUserMapping(user);

            // Return token + user details
            return new AuthResponse(token, userRespond);
        }

        throw new RuntimeException("Login failed! Please try again.");
    }

    private String SaveImage(MultipartFile file) throws IOException {
        return FileUploadingConfig.saveFile("upload/Userprofile", file);
    }
}
