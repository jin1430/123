package com.example.GoCafe.dto;

import com.example.GoCafe.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

    private Long userId;
    private String userEmail;
    private String userPassword;
    private String userNickname;
    private Long userAge;
    private String userGender;
    private String userRole;
    private java.time.LocalDateTime userDate;
    private String userPhoto;

    public User toEntity() {
        return new User(
                userId,
                userEmail,
                userPassword,
                userNickname,
                userAge,
                userGender,
                userRole,
                userDate,
                userPhoto
        );
    }
}
