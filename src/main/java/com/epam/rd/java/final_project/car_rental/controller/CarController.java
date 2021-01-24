package com.epam.rd.java.final_project.car_rental.controller;

import com.epam.rd.java.final_project.car_rental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @GetMapping("{user}")
//    public String userEditForm(@PathVariable User user, Model model) {
//        model.addAttribute(user);
//        return "user-edit";
//    }
//
//    @PostMapping
//    public String userSave(
//            @RequestParam("userId") User user,
//            @RequestParam String username,
//            @RequestParam(required = false) String manager
//    ) {
//        user.setUsername(username);
//
//        user.getRoles().clear();
//        user.getRoles().add(manager != null ? Role.MANAGER : Role.USER);
//
//        userRepository.save(user);
//        return "redirect:/user";
//    }
}
