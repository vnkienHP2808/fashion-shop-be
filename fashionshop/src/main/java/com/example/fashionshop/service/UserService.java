package com.example.fashionshop.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.fashionshop.dto.request.LoginRequest;
import com.example.fashionshop.dto.request.RegisterRequest;
import com.example.fashionshop.entity.User;

public interface UserService {
    String register(RegisterRequest request);
    Object login(LoginRequest request);
    boolean changePassword(Long userId, String oldPassword, String newPassword);
    User getUserById(Long id);
    User updateUser(Long id, User updatedUser);

    // lấy danh sách người dùng để check postman
    Page<User> getUsersByName(String name, int page, int size);
    void updatePhones(Long id, List<String> phones);
    void updateAddresses(Long id, List<String> addresses);
    User updateUserStatus(Long id, String status);

}
