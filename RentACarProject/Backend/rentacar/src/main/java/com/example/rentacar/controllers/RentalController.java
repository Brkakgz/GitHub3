package com.example.rentacar.controllers;

import com.example.rentacar.models.Rental;
import com.example.rentacar.models.User;
import com.example.rentacar.services.RentalService;
import com.example.rentacar.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;

    public RentalController(RentalService rentalService, UserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }

    // Aktif kiralamaları getirme
    @GetMapping("/active")
    public ResponseEntity<List<Rental>> getActiveRentals() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(rentalService.getActiveRentals(user));
    }

    // Tamamlanmış kiralamaları getirme
    @GetMapping("/completed")
    public ResponseEntity<List<Rental>> getCompletedRentals() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(rentalService.getCompletedRentals(user));
    }

    // Yeni kiralama oluşturma
    @PostMapping("/{carId}")
    public ResponseEntity<Rental> createRental(@PathVariable Long carId, @RequestBody Rental rental) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(rentalService.createRental(user, carId, rental));
    }

    // Belirli bir arabaya ait tüm kiralamaları getirme
    @GetMapping("/car/{carId}")
    public ResponseEntity<List<Rental>> getRentalsByCar(@PathVariable Long carId) {
        return ResponseEntity.ok(rentalService.getRentalsByCar(carId));
    }

    // Kiralama silme
    @DeleteMapping("/{rentalId}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long rentalId) {
        rentalService.deleteRentalById(rentalId); // Doğru metot adı kullanılacak şekilde güncellendi
        return ResponseEntity.noContent().build();
    }
}