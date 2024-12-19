package com.example.rentacar.services;

import com.example.rentacar.models.Car;
import com.example.rentacar.models.Rental;
import com.example.rentacar.models.User;
import com.example.rentacar.repositories.RentalRepository;
import com.example.rentacar.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RentalServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private RentalService rentalService;

    private Rental rental1;
    private Rental rental2;
    private User user;
    private Car car;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");

        car = new Car();
        car.setId(1L);
        car.setBrand("Toyota");

        rental1 = new Rental(1L, car, user, LocalDate.now().minusDays(5), LocalDate.now().plusDays(5), 500.0, false);
        rental2 = new Rental(2L, car, user, LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 200.0, true);
    }

    @Test
    void getActiveRentals_ShouldReturnActiveRentals() {
        when(rentalRepository.findByUserAndEndDateAfter(eq(user), any(LocalDate.class))).thenReturn(List.of(rental1));

        List<Rental> activeRentals = rentalService.getActiveRentals(user);

        assertEquals(1, activeRentals.size());
        verify(rentalRepository, times(1)).findByUserAndEndDateAfter(eq(user), any(LocalDate.class));
    }

    @Test
    void getCompletedRentals_ShouldReturnCompletedRentals() {
        when(rentalRepository.findByUserAndEndDateBefore(eq(user), any(LocalDate.class))).thenReturn(List.of(rental2));

        List<Rental> completedRentals = rentalService.getCompletedRentals(user);

        assertEquals(1, completedRentals.size());
        verify(rentalRepository, times(1)).findByUserAndEndDateBefore(eq(user), any(LocalDate.class));
    }

    @Test
    void createRental_ShouldSaveRental() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental1);

        Rental createdRental = rentalService.createRental(user, 1L, rental1);

        assertEquals(car.getBrand(), createdRental.getCar().getBrand());
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }
}
