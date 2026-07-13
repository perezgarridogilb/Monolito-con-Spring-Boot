package com.company.movie.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.movie.service.UserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ExistsByEmailValidation implements ConstraintValidator<ExistsByEmail,String> {
@Autowired
    private UserService userService;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService.existsByEmail(email).isEmpty();
    }

}
