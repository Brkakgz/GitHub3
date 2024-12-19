package com.example.rentacar.services;

import com.example.rentacar.models.Car;
import com.example.rentacar.repositories.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Sayfalama desteğiyle tüm arabaları getir.
     */
    public Page<Car> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    /**
     * ID'ye göre araba getir.
     */
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    /**
     * Yeni araba kaydet.
     */
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    /**
     * Araba sil.
     */
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}