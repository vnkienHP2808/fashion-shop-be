package com.example.fashionshop.service;

import java.util.List;

import com.example.fashionshop.dto.LoginRequest;
import com.example.fashionshop.dto.RegisterRequest;
import com.example.fashionshop.entity.User;

public interface UserService {
    String register(RegisterRequest request);
    Object login(LoginRequest request);
    boolean changePassword(Long userId, String oldPassword, String newPassword);
    User getUserById(Long id);
    User updateUser(Long id, User updatedUser);

    // lấy danh sách người dùng để check postman
    List<User> getAllUsers();

}
