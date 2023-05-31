package com.tamingthymeleaf.application.user.web;

import com.tamingthymeleaf.application.user.Email;
import com.tamingthymeleaf.application.user.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class NotExistingUserValidator implements ConstraintValidator<NotExistingUser, AbstractUserFormData> {
    private final UserService userService;

    @Autowired
    public NotExistingUserValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean isValid(AbstractUserFormData formData, ConstraintValidatorContext context) {
        if (userService.userWithEmailExists(new Email(formData.getEmail()))) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A user already exists with this email address").addPropertyNode("email").addConstraintViolation();
            return false;
        }
        return true;
    }
}
