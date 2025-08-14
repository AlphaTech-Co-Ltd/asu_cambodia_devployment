package com.asu_cambodia.asu.repository;

import com.asu_cambodia.asu.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByFirstNameAndLastNameAndUsername(String firstName, String lastName, String username);
    boolean existsByEmailIgnoreCase(String email);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(@NotBlank(message = "Username is required") String username);
}
