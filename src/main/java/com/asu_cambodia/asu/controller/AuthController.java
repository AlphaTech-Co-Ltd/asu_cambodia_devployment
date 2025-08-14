package com.asu_cambodia.asu.controller;

import com.asu_cambodia.asu.Service.impl.UserService;
import com.asu_cambodia.asu.dto.AuthResponse;
import com.asu_cambodia.asu.dto.UserDto.UserCheckerAccountLogIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/republic/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/logIn")
    public ResponseEntity<AuthResponse> login(@RequestBody UserCheckerAccountLogIn userCheckerAccountLogIn) {
        AuthResponse response = userService.CheckAuth(userCheckerAccountLogIn);
        return ResponseEntity.ok(response);
    }

}
