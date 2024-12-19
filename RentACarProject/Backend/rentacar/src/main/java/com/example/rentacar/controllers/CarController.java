package com.example.rentacar.controllers;

import com.example.rentacar.models.Car;
import com.example.rentacar.services.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CarController, arabaların verilerine ulaşmak için kullanılan API yollarını sağlar.
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     * Tüm arabaları getir (sayfalama desteği ile).
     */
    @GetMapping
    public ResponseEntity<Page<Car>> getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(carService.getAllCars(PageRequest.of(page, size)));
    }

    /**
     * ID'ye göre araba getir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Yeni araba ekle.
     */
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.saveCar(car));
    }

    /**
     * Araba sil.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
