package com.tandcode.final_project.car_rental.service;

import com.tandcode.final_project.car_rental.entity.Car;
import com.tandcode.final_project.car_rental.entity.CarBrand;
import com.tandcode.final_project.car_rental.repository.CarBrandRepository;
import com.tandcode.final_project.car_rental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    private CarBrandRepository carBrandRepository;

    public Page<Car> findPaginated(int currentPage, int pageSize, String carBrandFilter,
                                   String qualityClassFilter, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        Page<Car> page;

        //TODO refactor this and allow admin to see full list of cars with cars in usage
        if(!"".equals(carBrandFilter) && !"".equals(qualityClassFilter)) {
            page = carRepository.findAllByIsInUsageAndCarBrand_NameAndQualityClass_Name(false, carBrandFilter, qualityClassFilter, pageable);
        }
        else if(!"".equals(carBrandFilter)){
            page = carRepository.findAllByIsInUsageAndCarBrand_Name(false, carBrandFilter, pageable);
        }
        else if(!"".equals(qualityClassFilter)){
            page = carRepository.findAllByIsInUsageAndQualityClass_Name(false, qualityClassFilter, pageable);
        }
        else{
            page = carRepository.findAllByIsInUsage(false, pageable);
        }
        return page;
    }

    public List<CarBrand> findDistinctBrands() {
        return carBrandRepository.findAll();
    }
}
