package com.example.fashionshop.dto.request;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    @Size(min = 6, message = "Mật khẩu phải từ 6 ký tự trở lên!")
    private String password;
    private List<String> phones;
    private List<String> addresses;
}
