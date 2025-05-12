package com.example.fashionshop.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.fashionshop.dto.entity.UserDTO;
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

    @PutMapping("/{id}/phones")
    public ResponseEntity<Void> updatePhones(@PathVariable Long id, @RequestBody Map<String, List<String>> payload) {
        List<String> phones = payload.get("phones");
        userService.updatePhones(id, phones);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/addresses")
    public ResponseEntity<Void> updateAddresses(@PathVariable Long id, @RequestBody Map<String, List<String>> payload) {
        List<String> addresses = payload.get("addresses");
        userService.updateAddresses(id, addresses);
        return ResponseEntity.noContent().build();
    }
//~~~~~~~~~~~~~~~~~~~Admin~~~~~~~~~~~~~~~~~~~~~~~~
    @GetMapping()
    public ResponseEntity<Page<UserDTO>> getAllUserOrByName(
        @RequestParam(required = false, defaultValue = "") String name,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size) {
        Page<User> userPage = userService.getUsersByName(name, page, size);
        Page<UserDTO> userDTOPage = userPage.map(DTOMapper::toUserDTO);
        return userPage.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(userDTOPage);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<UserDTO> updateUserStatus(@PathVariable Long id, @RequestBody String status) {
        status = status.replace("\"", "");
        User updatedUser = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(DTOMapper.toUserDTO(updatedUser));
    }
}