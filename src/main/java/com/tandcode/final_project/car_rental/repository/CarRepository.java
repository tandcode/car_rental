package com.tandcode.final_project.car_rental.repository;

import com.tandcode.final_project.car_rental.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
//    List<Car> findByCarBrand(String brand);
    List<Car> findByQualityClass(Car.QualityClass qualityClass);
}
