package com.example.fashionshop.controller;

import com.example.fashionshop.dto.RegisterRequest;
import com.example.fashionshop.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173/")
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        String message = userService.register(request);
        return ResponseEntity.ok(message);
    }
}
