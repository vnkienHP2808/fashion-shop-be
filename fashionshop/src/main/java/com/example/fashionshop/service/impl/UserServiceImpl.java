package com.example.fashionshop.service.impl;

import com.example.fashionshop.dto.LoginRequest;
import com.example.fashionshop.dto.RegisterRequest;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.repository.UserRepository;
import com.example.fashionshop.service.UserService;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email đã tồn tại!";
        }

        //lưu thông tin người dùng vào cơ sở dữ liệu
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
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
            if (user.getPassword().equals(request.getPassword())) {
                return user; // hoặc trả về DTO nếu không muốn trả hết thông tin
            } else {
                return "Sai mật khẩu!";
            }
        } else {
            return "Tài khoản không tồn tại!";
        }
    }
}
