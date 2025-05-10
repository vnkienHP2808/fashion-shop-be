package com.example.fashionshop.service.impl;

import com.example.fashionshop.dto.request.LoginRequest;
import com.example.fashionshop.dto.request.RegisterRequest;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.repository.UserRepository;
import com.example.fashionshop.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email đã tồn tại!";
        }

        // lưu thông tin người dùng vào cơ sở dữ liệu
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                // .password(passwordEncoder.encode(request.getPassword()))
                .password(request.getPassword())
                .status("Active")
                .role("Customer")
                .phones(request.getPhones())
                .addresses(request.getAddresses())
                .build();
        userRepository.save(user);

        return "Đăng ký tài khoản thành công!";
    }

    @Override
    public Object login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            if (user.getPassword().equals(request.getPassword())) {
                return user;
            } else {
                return "Sai mật khẩu!";
            }
        } else {
            return "Tài khoản không tồn tại!";
        }
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Kiểm tra mật khẩu cũ đã mã hóa
            // if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            //     // Mã hóa mật khẩu mới trước khi lưu
            //     user.setPassword(passwordEncoder.encode(newPassword));
            if (user.getPassword().equals(oldPassword)) {
                user.setPassword(newPassword);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setAddresses(updatedUser.getAddresses());
        user.setPhones(updatedUser.getPhones());
        return userRepository.save(user);
    }

    @Override
    public void updatePhones(Long id, List<String> phones) {
        User user = userRepository.findById(id).orElseThrow();
        user.setPhones(phones);
        userRepository.save(user);
    }

    @Override
    public void updateAddresses(Long id, List<String> addresses) {
        User user = userRepository.findById(id).orElseThrow();
        user.setAddresses(addresses);
        userRepository.save(user);
    }
//~~~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~~~

    @Override
    public Page<User> getUsersByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> userPage = userRepository.findUsersByName(name, pageable);
        return userPage;
    }

    @Override
    public User updateUserStatus(Long id, String status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        if ("Admin".equalsIgnoreCase(user.getRole()) && "Inactive".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException("Admin không thể bị hủy kích hoạt.");
        }

        user.setStatus(status);
        return userRepository.save(user);
    }

}
