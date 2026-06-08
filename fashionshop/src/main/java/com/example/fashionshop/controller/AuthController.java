package com.example.fashionshop.controller;

import com.example.fashionshop.dto.request.ChangePasswordRequest;
import com.example.fashionshop.dto.request.LoginRequest;
import com.example.fashionshop.dto.request.RegisterRequest;
import com.example.fashionshop.dto.response.TokenResponse;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.service.UserService;
import com.example.fashionshop.util.DTOMapper;
import com.example.fashionshop.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private UserService userService;
    
    private final JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public String register(@RequestBody @Valid RegisterRequest request) {
        return userService.register(request);
    }
    
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.login(request);
        
        String accessToken = jwtUtil.generateAccessToken(user.getId_user(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId_user(), user.getRole());

        return ResponseEntity.ok(new TokenResponse(
            accessToken,
            refreshToken,
            DTOMapper.toLoginResponseDTO(user)
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        if (refreshToken == null || !jwtUtil.isValid(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token không hợp lệ");
        }

        Long userId = jwtUtil.extractUserId(refreshToken);
        String role  = jwtUtil.extractRole(refreshToken);

        String newAccessToken  = jwtUtil.generateAccessToken(userId, role);
        String newRefreshToken = jwtUtil.generateRefreshToken(userId, role);

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken
        ));
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