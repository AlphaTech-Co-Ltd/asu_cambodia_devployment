package com.asu_cambodia.asu.dto.UserDto;

import com.asu_cambodia.asu.enumStirng.RoleUser;

public record UserCheckerAccountLogIn(
        String username,
        String password,
        RoleUser roleUser
) {
}