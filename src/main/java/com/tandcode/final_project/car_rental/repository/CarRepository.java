package com.tandcode.final_project.car_rental.repository;

import com.tandcode.final_project.car_rental.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findAllByCarBrand_NameAndQualityClass_Name(String carBrandName, String qualityClassName, Pageable pageable);
    Page<Car> findAllByCarBrand_Name(String name, Pageable pageable);
    Page<Car> findAllByQualityClass_Name(String name, Pageable pageable);
}
