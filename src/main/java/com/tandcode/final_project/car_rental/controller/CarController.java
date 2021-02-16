package com.tandcode.final_project.car_rental.controller;

import com.tandcode.final_project.car_rental.entity.Car;
import com.tandcode.final_project.car_rental.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    private String carList(Model model,
                           Authentication authentication,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("carBrandFilter") Optional<String> filteringCarBrand,
                           @RequestParam("qualityClassFilter") Optional<String> filteringQualityClass,
                           @RequestParam("sortField") Optional<String> sortingField,
                           @RequestParam("sortDir") Optional<String> sortingDir
    ){
        carService.getCarList(model, authentication, page, size,
                filteringCarBrand, filteringQualityClass, sortingField, sortingDir);

        return "car-list";
    }


    @GetMapping("/create")
    private String carCreate(Model model){
        model.addAttribute("car", new Car());
        model.addAttribute("qualityClasses", carService.findAllQualityClasses());
        return "car-create";
    }

    @PostMapping("/create")
    public String carSave(@Valid @ModelAttribute("car") Car car,
                          Errors errors,
                          @RequestParam("carBrandName") String carBrandName,
                          @RequestParam("qualityClassId") String qualityClassId
                          ) {
        if (errors.hasErrors()) {
            return "car-create";
        }
        carService.saveCar(car, carBrandName, qualityClassId, "Creating car: ");
        return "redirect:/car";
    }

    @GetMapping("/edit/{carId}")
    private String carEdit(@PathVariable Long carId,
                           Model model){
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        model.addAttribute("carBrandName", car.getCarBrand().getName());
        model.addAttribute("qualityClasses", carService.findAllQualityClasses());
        return "car-edit";
    }

    @PostMapping("/edit/{carId}")
    public String carEditSave(@Valid @ModelAttribute("car") Car car,
                              @RequestParam("qualityClassId") String qualityClassId,
                              Errors errors,
                              Model model) {
        if (errors.hasErrors()) {
            return "car-edit";
        }
        carService.saveCar(car, car.getCarBrand().getName(), qualityClassId, "Editing car: ");
        return "redirect:/car";
    }

    @GetMapping("/delete/{carId}")
    private String carDelete(@PathVariable Long carId,
                           Model model){

        carService.deleteById(carId);
        return "redirect:/car";
    }
}
