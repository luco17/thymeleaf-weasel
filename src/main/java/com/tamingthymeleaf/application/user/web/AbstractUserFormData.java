package com.tamingthymeleaf.application.user.web;

import com.tamingthymeleaf.application.infrastructure.validation.ValidationGroupOne;
import com.tamingthymeleaf.application.infrastructure.validation.ValidationGroupTwo;
import com.tamingthymeleaf.application.user.Gender;
import com.tamingthymeleaf.application.user.UserRole;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NotExistingUser(groups = ValidationGroupTwo.class)
public class AbstractUserFormData {
    @NotNull
    private UserRole userRole;

    @NotBlank(message = "Please enter a first name")
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String firstName;

    @NotBlank(message = "Please enter a last name")
    @Size(min = 1, max = 200, groups = ValidationGroupOne.class)
    private String lastName;

    @NotNull(message = "Please pick a gender")
    private Gender gender;

    @NotBlank(message = "Please enter a valid email address")
    @Email(groups = ValidationGroupOne.class)
    private String email;

    @NotNull(message = "Please enter a birthdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank(message = "Please enter a valid phone number")
    @Pattern(regexp = "[0-9.\\-() x/+]+", message = "Must be a valid phone number", groups = ValidationGroupOne.class)
    private String phoneNumber;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
