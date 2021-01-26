package com.epam.rd.java.final_project.car_rental.controller;

import com.epam.rd.java.final_project.car_rental.entity.Car;
import com.epam.rd.java.final_project.car_rental.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarRepository carRepository;

    @GetMapping
    private String carList(Model model){
        model.addAttribute("cars", carRepository.findAll());
        return "car-list";
    }

    @GetMapping("/create")
    private String carCreate(Model model){
        model.addAttribute("car", new Car());
        return "car-create";
    }

//    @ModelAttribute(name = "car")
//    public Car car() {
//        return new Car();
//    }

    @PostMapping("/create")
    public String userSave(@Valid @ModelAttribute("car") Car car,
                           Model model) {
        carRepository.save(car);
        return "redirect:/car";
    }
}
