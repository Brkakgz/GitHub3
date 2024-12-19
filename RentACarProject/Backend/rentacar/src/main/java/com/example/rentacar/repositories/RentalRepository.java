package com.example.rentacar.repositories;

import com.example.rentacar.models.Car;
import com.example.rentacar.models.Rental;
import com.example.rentacar.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserAndEndDateAfter(User user, LocalDate date);
    List<Rental> findByUserAndEndDateBefore(User user, LocalDate date);
    List<Rental> findByCar(Car car);
}
