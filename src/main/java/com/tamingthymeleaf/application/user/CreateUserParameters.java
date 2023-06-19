package com.tamingthymeleaf.application.user;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class CreateUserParameters {
    private final UserName userName;
    private final String password;
    private final Gender gender;
    private final LocalDate birthday;
    private final Email email;
    private final PhoneNumber phoneNumber;
    private final UserRole userRole;
    private MultipartFile avatar;

    public CreateUserParameters(UserName userName, String password, Gender gender, LocalDate birthday, Email email, PhoneNumber phoneNumber, UserRole userRole) {
        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
    }

    public UserName getUserName() {
        return userName;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Email getEmail() {
        return email;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    @Nullable
    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }
}
