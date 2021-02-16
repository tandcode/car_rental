package com.tandcode.final_project.car_rental.controller;

import com.tandcode.final_project.car_rental.entity.Passport;
import com.tandcode.final_project.car_rental.entity.RentOrder;
import com.tandcode.final_project.car_rental.entity.User;
import com.tandcode.final_project.car_rental.service.CarService;
import com.tandcode.final_project.car_rental.service.RentOrderService;
import com.tandcode.final_project.car_rental.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/car")
public class RentOrderController {

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private RentOrderService rentOrderService;

    @GetMapping("/rent/{carId}")
    private String carRent(Model model){
        return "car-rent";
    }

    @ModelAttribute
    public void fillModel(@PathVariable Long carId, Model model){
        RentOrder rentOrder = RentOrder.builder()
                .car(carService.findById(carId))
                .passport(new Passport())
                .build();
        model.addAttribute("rentOrder", rentOrder);
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal) {
        String username = principal.getName();
        User user = userService.loadUserByUsername(username);
        return user;
    }

    @PostMapping("/rent/{carId}")
    public String processRent(@Valid RentOrder rentOrder,
                              Errors errors,
                              @ModelAttribute("user") User user,
                              Model model) {
        if (errors.hasErrors()) {
            return "car-rent";
        }
        rentOrderService.processOrder(rentOrder, user);
        return "redirect:/car";
    }
}
