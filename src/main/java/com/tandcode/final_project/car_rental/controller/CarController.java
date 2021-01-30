package com.tandcode.final_project.car_rental.controller;

import com.tandcode.final_project.car_rental.entity.Car;
import com.tandcode.final_project.car_rental.entity.CarBrand;
import com.tandcode.final_project.car_rental.repository.CarBrandRepository;
import com.tandcode.final_project.car_rental.repository.CarRepository;
import com.tandcode.final_project.car_rental.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    private CarService carService;

    @GetMapping
    private String carList(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("filterField") Optional<String> filteringField,
                           @RequestParam("filterValue") Optional<String> filteringValue,
                           @RequestParam("sortField") Optional<String> sortingField,
                           @RequestParam("sortDir") Optional<String> sortingDir
    ){
        // TODO migrate all logic from controllers to services
        // TODO Optional: regional standards like money could be convertable based on language
        // TODO Binding exception handler
        // TODO save -> try catch + transactional
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        String filterField = filteringField.orElse(null);
        String filterValue = filteringValue.orElse(null);
        String sortField = sortingField.orElse("carBrand");
        String sortDir = sortingDir.orElse("asc");

        Page<Car> carPage = carService.findPaginated(
                currentPage,
                pageSize,
                filterField,
                filterValue,
                sortField,
                sortDir);

        model.addAttribute("carPage", carPage);
        model.addAttribute("sortField", sortField);
        model.addAttribute("filterField", filterField);
        model.addAttribute("filterValue", filterValue);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        int totalPages = carPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "car-list";
    }


    @GetMapping("/rent/{carId}")
    private String carRent(@PathVariable Long carId,
                           Model model){
        Optional<Car> car = carRepository.findById(carId);
        model.addAttribute("car", car.orElseThrow());
        return "car-rent";
    }

    @GetMapping("/create")
    private String carCreate(Model model){
        model.addAttribute("car", new Car());
        model.addAttribute("carBrand", new CarBrand());
        return "car-create";
    }

    @PostMapping("/create")
    public String carSave(@Valid @ModelAttribute("car") Car car,
                           @Valid @ModelAttribute("carBrand") CarBrand carBrand,
                           Errors errors,
                           Model model) {
        if (errors.hasErrors()) {
            return "car-create";
        }
        //TODO implement transactional
        Optional<CarBrand> carBrandFromRepository = carBrandRepository.findByName(carBrand.getName());
        car.setCarBrand(carBrandFromRepository.orElse(carBrand));
        log.info("Creating car: " + car);
        carRepository.save(car);
        return "redirect:/car";
    }

    @GetMapping("/edit/{carId}")
    private String carEdit(@PathVariable Long carId,
                           Model model){
        model.addAttribute("car", carRepository.findById(carId).orElseThrow());
        return "car-edit";
    }

    @PostMapping("/edit/{carId}")
    public String carEditSave(@Valid @ModelAttribute("car") Car car,
                           Errors errors,
                           Model model) {
        if (errors.hasErrors()) {
            return "car-create";
        }

        log.info("Editing car: " + car);
        carRepository.save(car);
        return "redirect:/car";
    }

    @GetMapping("/delete/{carId}")
    private String carDelete(@PathVariable Long carId,
                           Model model){
//        log.info("Creating car: " + car);
        model.addAttribute("car", carRepository.findById(carId).orElseThrow());
        return "car-edit";
    }
}
