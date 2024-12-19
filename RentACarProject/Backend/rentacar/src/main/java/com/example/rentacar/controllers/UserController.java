package com.example.rentacar.controllers;

import com.example.rentacar.models.User;
import com.example.rentacar.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Kullanıcı profil bilgilerini getirme
    @GetMapping("/me")
    public ResponseEntity<User> getUserProfile() {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found")));
    }

    // Kullanıcı profil bilgilerini güncelleme
    @PutMapping("/me")
    public ResponseEntity<User> updateUserProfile(@RequestBody User updatedUser) {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.updateUser(username, updatedUser));
    }

    // Tüm kullanıcıları getirme (Admin için)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // Kullanıcı yoksa 204 No Content döner
        }
        return ResponseEntity.ok().body(users); // Tüm kullanıcıları döner
    }

    // Kullanıcı silme (Admin için)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
