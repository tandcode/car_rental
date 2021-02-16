package com.tandcode.final_project.car_rental.service;

import com.tandcode.final_project.car_rental.entity.Car;
import com.tandcode.final_project.car_rental.entity.CarBrand;
import com.tandcode.final_project.car_rental.entity.QualityClass;
import com.tandcode.final_project.car_rental.repository.CarBrandRepository;
import com.tandcode.final_project.car_rental.repository.CarRepository;
import com.tandcode.final_project.car_rental.repository.QualityClassRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    CarBrandRepository carBrandRepository;

    @Autowired
    QualityClassRepository qualityClassRepository;

    public Page<Car> findPaginated(Authentication authentication, int currentPage, int pageSize, String carBrandFilter,
                                   String qualityClassFilter, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

        Specification<Car> carBrandSpec = CarSpecs.carCarBrandNameEquals(carBrandFilter);
        Specification<Car> qualityClassSpec = CarSpecs.carQualityClassNameEquals(qualityClassFilter);
        Specification<Car> notInUsageSpec = CarSpecs.carIsInUsageEquals(false);

        Specification<Car> userSpec = Specification.where(carBrandSpec).and(qualityClassSpec).and(notInUsageSpec);
        Specification<Car> adminSpec = Specification.where(carBrandSpec).and(qualityClassSpec);

        return carRepository.findAll(isAdmin ? adminSpec : userSpec, pageable);
    }

    public List<CarBrand> findAllBrands() {
        return carBrandRepository.findAll();
    }

    public List<QualityClass> findAllQualityClasses() {
        return qualityClassRepository.findAll();
    }
    public Optional<CarBrand> findCarBrandByName(String carBrandName) {
        return carBrandRepository.findByName(carBrandName);
    }

    public void getCarList(Model model,
                            Authentication authentication,
                            Optional<Integer> page,
                            Optional<Integer> size,
                            Optional<String> filteringCarBrand,
                            Optional<String> filteringQualityClass,
                            Optional<String> sortingField,
                            Optional<String> sortingDir) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        String carBrandFilter = filteringCarBrand.orElse("");
        String qualityClassFilter = filteringQualityClass.orElse("");
        String sortField = sortingField.orElse("carBrand");
        String sortDir = sortingDir.orElse("asc");

        Page<Car> carPage = findPaginated(
                authentication,
                currentPage,
                pageSize,
                carBrandFilter,
                qualityClassFilter,
                sortField,
                sortDir);

        model.addAttribute("carPage", carPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("carBrandFilter", carBrandFilter);
        model.addAttribute("qualityClassFilter", qualityClassFilter);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("carBrands", findAllBrands());
        model.addAttribute("qualityClasses", findAllQualityClasses());
    }

    public Car findById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid car id: " + id));
    }

    @Transactional
    public void deleteById(Long id) {
        log.info("Deleting car: " +  findById(id));
        carRepository.deleteById(id);
    }

    @Transactional
    public void saveCar(Car car, String carBrandName, String qualityClassId, String logMessage) {
        Optional<CarBrand> carBrandFromRepository = findCarBrandByName(carBrandName);
        CarBrand newCarBrand = CarBrand.builder().name(carBrandName).build();
        if (carBrandFromRepository.isEmpty()) {
            carBrandRepository.save(newCarBrand);
        }
        Optional<QualityClass> qualityClassFromRepository = qualityClassRepository.findById(Integer.valueOf(qualityClassId));

        car.setCarBrand(carBrandFromRepository.orElse(newCarBrand));
        car.setQualityClass(qualityClassFromRepository.orElseThrow());

        log.info(logMessage + car);
        carRepository.save(car);
    }
}
