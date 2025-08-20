package com.asu_cambodia.asu.model;

import com.asu_cambodia.asu.enumStirng.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "student_tbl")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String firstName;
    @Column(length = 50, nullable = false)
    private String lastName;
    @Column(length = 250, nullable = false)
    private String email;
    @Column(length = 15, nullable = false)
    private String phoneNumber;
    @Column(length = 50, nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(length = 150, nullable = false)
    private String address;
    @Column(length = 150, nullable = false)
    private String city;
    @Column(length = 50, nullable = false)
    private String state;
    @Column(length = 50, nullable = false)
    private String country;

    @Column(updatable = false)
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
