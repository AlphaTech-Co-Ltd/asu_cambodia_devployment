package com.asu_cambodia.asu.Service;

import com.asu_cambodia.asu.dto.UserDto.PasswordUpdateRequest;
import com.asu_cambodia.asu.dto.UserDto.UserRequest;
import com.asu_cambodia.asu.dto.UserDto.UserRespond;

import java.io.IOException;
import java.util.List;

public interface IUserService{
    // method reader to get data and binding data
    List<UserRespond> ReadAllUsersData(); // get all user from db to frontend side.

    // get user by id to show there profile in frontend size
    UserRespond GetUserById(Long id);

    UserRespond updateUserPassword(Long id, String currentPassword, String newPassword);

    // method create the user
    UserRespond createUser(UserRequest userRequest) throws IOException;

    // method update user account to change the data
    UserRespond updateUser(Long id, UserRequest userRequest) throws IOException;

    void deleteUser(Long id) throws IOException;
}
