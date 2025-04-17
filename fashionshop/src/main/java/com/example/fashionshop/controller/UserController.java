

package com.example.fashionshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionshop.dto.UserDTO;
import com.example.fashionshop.entity.User;
import com.example.fashionshop.service.UserService;
import com.example.fashionshop.util.DTOMapper;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(DTOMapper.toUserDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(DTOMapper.toUserDTO(user));
    }

    // lấy danh sách người dùng để check postman
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(DTOMapper.toUserDTOList(users));
    }

    @PutMapping("/{id}/phones")
public ResponseEntity<?> updatePhones(@PathVariable Long id, @RequestBody Map<String, List<String>> payload) {
    List<String> phones = payload.get("phones");
    userService.updatePhones(id, phones);
    return ResponseEntity.ok().build();
}

@PutMapping("/{id}/addresses")
public ResponseEntity<?> updateAddresses(@PathVariable Long id, @RequestBody Map<String, List<String>> payload) {
    List<String> addresses = payload.get("addresses");
    userService.updateAddresses(id, addresses);
    return ResponseEntity.ok().build();
}

}