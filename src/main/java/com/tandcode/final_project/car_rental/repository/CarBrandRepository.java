package com.tandcode.final_project.car_rental.repository;

import com.tandcode.final_project.car_rental.entity.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarBrandRepository extends JpaRepository<CarBrand, Long> {
    Optional<CarBrand> findByName(String name);
}
