package com.tandcode.final_project.car_rental.repository;

import com.tandcode.final_project.car_rental.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findAllByIsInUsageAndCarBrand_NameAndQualityClass_Name(Boolean isInUsage, String carBrandName,
                                                                     String qualityClassName, Pageable pageable);
    Page<Car> findAllByIsInUsageAndCarBrand_Name(Boolean isInUsage, String name, Pageable pageable);
    Page<Car> findAllByIsInUsageAndQualityClass_Name(Boolean isInUsage, String name, Pageable pageable);
    Page<Car> findAllByIsInUsage(Boolean isInUsage, Pageable pageable);
}
