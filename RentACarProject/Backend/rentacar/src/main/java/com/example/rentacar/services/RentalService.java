package com.example.rentacar.services;

import com.example.rentacar.models.Car;
import com.example.rentacar.models.Rental;
import com.example.rentacar.models.User;
import com.example.rentacar.repositories.RentalRepository;
import com.example.rentacar.repositories.CarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;

    public RentalService(RentalRepository rentalRepository, CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
    }

    public List<Rental> getActiveRentals(User user) {
        return rentalRepository.findByUserAndEndDateAfter(user, LocalDate.now());
    }

    public List<Rental> getCompletedRentals(User user) {
        return rentalRepository.findByUserAndEndDateBefore(user, LocalDate.now());
    }

    public Rental createRental(User user, Long carId, Rental rental) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        rental.setUser(user);
        rental.setCar(car);
        rental.setStartDate(LocalDate.now());
        rental.setTotalPrice(rental.getCar().getDailyPrice() * (rental.getEndDate().toEpochDay() - rental.getStartDate().toEpochDay()));
        rental.setCompleted(false);
        return rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public List<Rental> getRentalsByCar(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        return rentalRepository.findByCar(car);
    }

    public void deleteRentalById(Long rentalId) {
        rentalRepository.deleteById(rentalId); // Kiralama silme işlemi
    }
}
