package com.tandcode.final_project.car_rental.repository;

import com.tandcode.final_project.car_rental.entity.QualityClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QualityClassRepository extends JpaRepository<QualityClass, Integer> {
    Optional<QualityClass> findByName(String name);
}
