package com.example.fashionshop.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id_user;
    private String name;
    private String email;
    private String status;
    private String role;
    private List<String> phones;
    private List<String> addresses;
    private List<OrderDTO> orders;
    private CartDTO cart;
}