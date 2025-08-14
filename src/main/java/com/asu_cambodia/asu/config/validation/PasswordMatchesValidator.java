package com.asu_cambodia.asu.config.validation;

import com.asu_cambodia.asu.dto.UserDto.UserRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRequest> {

    @Override
    public boolean isValid(UserRequest request, ConstraintValidatorContext context) {
        if (request.password() == null || request.confirmPassword() == null) {
            return false;
        }
        return request.password().equals(request.confirmPassword());
    }
}