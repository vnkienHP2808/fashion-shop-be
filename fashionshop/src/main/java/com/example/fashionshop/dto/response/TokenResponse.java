package com.example.fashionshop.dto.response;

import lombok.*;

@Data @AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private LoginResponseDTO user;
}