package com.example.rentacar.controllers;

import com.example.rentacar.models.Car;
import com.example.rentacar.models.Rental;
import com.example.rentacar.models.User;
import com.example.rentacar.services.CarService;
import com.example.rentacar.services.RentalService;
import com.example.rentacar.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final CarService carService;
    private final RentalService rentalService;
    private final UserService userService;

    public AdminController(CarService carService, RentalService rentalService, UserService userService) {
        this.carService = carService;
        this.rentalService = rentalService;
        this.userService = userService;
    }

    // Tüm kullanıcıları listeleme
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Belirli bir kullanıcıyı silme
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Tüm kiralamaları listeleme
    @GetMapping("/rentals")
    public ResponseEntity<List<Rental>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    // Belirli bir arabaya ait kiralamaları listeleme
    @GetMapping("/rentals/car/{carId}")
    public ResponseEntity<List<Rental>> getRentalsByCar(@PathVariable Long carId) {
        return ResponseEntity.ok(rentalService.getRentalsByCar(carId));
    }

    // Yeni araba ekleme
    @PostMapping("/cars")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.saveCar(car));
    }

    // Mevcut bir arabayı silme
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
