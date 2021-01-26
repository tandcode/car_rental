package com.epam.rd.java.final_project.car_rental.service;

import com.epam.rd.java.final_project.car_rental.entity.Car;
import com.epam.rd.java.final_project.car_rental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public Page<Car> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Car> list;

        if (carRepository.findAll().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, carRepository.findAll().size());
            list = carRepository.findAll().subList(startItem, toIndex);
        }

        Page<Car> bookPage
                = new PageImpl<Car>(list, PageRequest.of(currentPage, pageSize), carRepository.findAll().size());

        return bookPage;
    }
}
