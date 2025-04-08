package com.example.fashionshop.service;

import com.example.fashionshop.dto.LoginRequest;
import com.example.fashionshop.dto.RegisterRequest;

public interface UserService {
    String register(RegisterRequest request);
    Object login(LoginRequest request);
}
