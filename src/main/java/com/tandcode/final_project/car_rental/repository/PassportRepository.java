package com.tandcode.final_project.car_rental.repository;

import com.tandcode.final_project.car_rental.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, Long> {
    Optional<Passport> findByPassportNumber(String passportNumber);
}
