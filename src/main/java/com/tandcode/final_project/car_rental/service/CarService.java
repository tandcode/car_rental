package com.tandcode.final_project.car_rental.service;

import com.tandcode.final_project.car_rental.entity.Car;
import com.tandcode.final_project.car_rental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    public Page<Car> findPaginated(int currentPage, int pageSize, String filterField, String filterValue, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

            return carRepository.findAll(pageable);
//
//        switch (filterField) {
//            case
//        }
    }

//    public List<String> findDistinctBrands() {
//        carRepository.find
//    }
}
