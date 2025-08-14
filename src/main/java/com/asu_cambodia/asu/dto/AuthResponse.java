package com.asu_cambodia.asu.dto;

import com.asu_cambodia.asu.dto.UserDto.UserRespond;

public record AuthResponse(
        String token,
        UserRespond user
) {}
