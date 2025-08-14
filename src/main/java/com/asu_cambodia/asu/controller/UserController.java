package com.asu_cambodia.asu.controller;

import com.asu_cambodia.asu.Service.impl.UserService;
import com.asu_cambodia.asu.dto.UserDto.UserRequest;
import com.asu_cambodia.asu.dto.UserDto.UserRespond;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserRespond>> readAllDataUsers(){
        List<UserRespond> reader = userService.ReadAllUsersData();
        return ResponseEntity.status(HttpStatus.OK).body(reader);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/create")
    public ResponseEntity<?> createUserAccount(@ModelAttribute UserRequest userRequest){
        try{
            UserRespond userRespond = userService.createUser(userRequest);
            return ResponseEntity.status(HttpStatus.OK).body(userRespond);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = {"/update/{id}"})
    public ResponseEntity<?> updateUserAccount(@PathVariable Long id, @ModelAttribute UserRequest userRequest){
        try{
            UserRespond userRespond = userService.updateUser(id, userRequest);
            return ResponseEntity.status(HttpStatus.OK).body(userRespond);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserAccount(@PathVariable Long id)throws Exception{
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted User");
    }
}
