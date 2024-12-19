package com.example.rentacar.services;

import com.example.rentacar.models.Car;
import com.example.rentacar.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car car1;
    private Car car2;

    @BeforeEach
    void setUp() {
        car1 = new Car(1L, "Toyota", "Corolla", "White", true, 100, 150.0);
        car2 = new Car(2L, "Honda", "Civic", "Black", false, 120, 200.0);
    }

    @Test
    void getAllCars_ShouldReturnCarList() {
        Pageable pageable = PageRequest.of(0, 10);
        when(carRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(car1, car2)));

        Page<Car> cars = carService.getAllCars(pageable);

        assertNotNull(cars);
        assertEquals(2, cars.getContent().size());
        assertEquals("Toyota", cars.getContent().get(0).getBrand());
        verify(carRepository, times(1)).findAll(pageable);
    }

    @Test
    void saveCar_ShouldSaveCar() {
        when(carRepository.save(car1)).thenReturn(car1);

        Car savedCar = carService.saveCar(car1);

        assertNotNull(savedCar);
        assertEquals("Toyota", savedCar.getBrand());
        verify(carRepository, times(1)).save(car1);
    }

    @Test
    void getCarById_ShouldReturnCar() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car1));

        Optional<Car> foundCar = carService.getCarById(1L);

        assertTrue(foundCar.isPresent());
        assertEquals("Toyota", foundCar.get().getBrand());
        verify(carRepository, times(1)).findById(1L);
    }
}
