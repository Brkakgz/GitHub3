package com.example.rentacar.repositories;

import com.example.rentacar.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
