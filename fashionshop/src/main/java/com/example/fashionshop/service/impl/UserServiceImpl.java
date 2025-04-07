package com.example.fashionshop.service.impl;

import com.example.fashionshop.dto.RegisterRequest;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.repository.UserRepository;
import com.example.fashionshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email đã tồn tại!";
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .status("Active")
                .role("Customer")
                .build();

        userRepository.save(user);
        return "Đăng ký tài khoản thành công!";
    }
}
