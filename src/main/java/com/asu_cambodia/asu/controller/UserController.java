package com.asu_cambodia.asu.controller;

import com.asu_cambodia.asu.Service.impl.UserService;
import com.asu_cambodia.asu.dto.UserDto.PasswordUpdateRequest;
import com.asu_cambodia.asu.dto.UserDto.UserRequest;
import com.asu_cambodia.asu.dto.UserDto.UserRespond;
import com.asu_cambodia.asu.excption.customException.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/all/userData")
    public ResponseEntity<List<UserRespond>> readAllDataUsers(){
        List<UserRespond> reader = userService.ReadAllUsersData();
        return ResponseEntity.status(HttpStatus.OK).body(reader);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRespond> getUserById(@PathVariable Long id){
        UserRespond userRespond = userService.GetUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userRespond);
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

    @PutMapping("/{id}/password")
    public ResponseEntity<?> updatePassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) { // using Map to accept currentPassword + newPassword
        try {
            String currentPassword = body.get("currentPassword");
            String newPassword = body.get("newPassword");

            if (currentPassword == null || newPassword == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Both currentPassword and newPassword are required"));
            }

            UserRespond updatedUser = userService.updateUserPassword(id, currentPassword, newPassword);
            return ResponseEntity.ok(updatedUser);

        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", ex.getMessage()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Something went wrong"));
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserAccount(@PathVariable Long id)throws Exception{
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted User");
    }
}
