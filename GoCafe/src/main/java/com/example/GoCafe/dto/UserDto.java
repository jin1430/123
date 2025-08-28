package com.example.GoCafe.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private String userEmail;
    private String userNickname;
    private Integer userAge;
    private String userGender;
}