package com.epam.rd.java.final_project.car_rental.service;

import com.epam.rd.java.final_project.car_rental.entity.Car;
import com.epam.rd.java.final_project.car_rental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public Page<Car> findPaginated(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

//    public Iterable<Car> findSorted(Sort sort) {
//
//    }
}
