package com.example.rentacar.repositories;

import com.example.rentacar.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByNationalId(String nationalId);
}
