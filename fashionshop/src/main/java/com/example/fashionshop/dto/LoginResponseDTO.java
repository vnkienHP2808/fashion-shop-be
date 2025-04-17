package com.example.fashionshop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id_user;
    private String name;
    private String email;
    private String role;
    private String status;
    private List<String> phones;
    private List<String> addresses;
    private List<OrderDTO> orders;
    private CartDTO cart;
}