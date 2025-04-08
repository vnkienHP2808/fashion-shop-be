package com.example.fashionshop.controller;

import com.example.fashionshop.dto.ChangePasswordRequest;
import com.example.fashionshop.dto.LoginRequest;
import com.example.fashionshop.dto.RegisterRequest;
import com.example.fashionshop.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        String message = userService.register(request);
        return ResponseEntity.ok(message);
    }
    
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        boolean success = userService.changePassword(
                request.getUserId(),
                request.getOldPassword(),
                request.getNewPassword());

        if (success) {
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } else {
            return ResponseEntity.badRequest().body("Mật khẩu cũ không đúng!");
        }
    }

}
