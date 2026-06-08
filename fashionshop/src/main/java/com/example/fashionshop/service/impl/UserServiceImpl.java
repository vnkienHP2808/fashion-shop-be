package com.example.fashionshop.service.impl;

import com.example.fashionshop.dto.request.LoginRequest;
import com.example.fashionshop.dto.request.RegisterRequest;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.exception.AuthenticationException;
import com.example.fashionshop.exception.BadRequestException;
import com.example.fashionshop.repository.UserRepository;
import com.example.fashionshop.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại!");
        }

        // lưu thông tin người dùng vào cơ sở dữ liệu
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .status("Active")
                .role("Customer")
                .phones(request.getPhones())
                .addresses(request.getAddresses())
                .build();
        userRepository.save(user);

        return "Đăng ký tài khoản thành công!";
    }

    @Override
    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new AuthenticationException("Tài khoản không tồn tại!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new AuthenticationException("Sai mật khẩu!");
    }
    return user;
    }

    @Override
    @Transactional
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // if (passwordEncoder.matches(oldPassword, user.getPassword())) {
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        throw new AuthenticationException("Mật khẩu cũ không đúng!");
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void updatePhones(Long id, List<String> phones) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPhones(phones);
        userRepository.save(user);
    }

    @Override
    public void updateAddresses(Long id, List<String> addresses) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setAddresses(addresses);
        userRepository.save(user);
    }
//~~~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public Page<User> getUsersByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> userPage = userRepository.findUsersByName(name, pageable);
        return userPage;
    }

    @Override
    public User updateUserStatus(Long id, String status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if ("Admin".equalsIgnoreCase(user.getRole()) && "Inactive".equalsIgnoreCase(status)) {
            throw new BadRequestException("Admin không thể bị hủy kích hoạt.");
        }

        user.setStatus(status);
        return userRepository.save(user);
    }
}
