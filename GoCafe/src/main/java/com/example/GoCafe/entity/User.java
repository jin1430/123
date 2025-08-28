package com.example.GoCafe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "user_email", nullable = false, unique = true, length = 20)
    private String userEmail;

    @Column(name = "user_password", nullable = false, length = 20)
    private String userPassword;

    @Column(name = "user_nickname", nullable = false, unique = true, length = 8)
    private String userNickname;

    @Column(name = "user_age")
    private Long userAge;

    @Column(name = "user_gender", length = 1)
    private String userGender;

    @Column(name = "user_role", nullable = false, length = 5)
    private String userRole;

    @Column(name = "user_date", nullable = false)
    private java.time.LocalDateTime userDate;

    @Column(name = "user_photo", length = 30)
    private String userPhoto;
}