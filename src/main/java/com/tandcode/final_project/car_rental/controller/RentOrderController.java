package com.tandcode.final_project.car_rental.controller;

import com.tandcode.final_project.car_rental.entity.Car;
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
import org.springframework.validation.BindingResult;
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
        model.addAttribute("car", carService.findById(carId));
        model.addAttribute("rentOrder", new RentOrder());
        model.addAttribute("passport", new Passport());
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal) {
        String username = principal.getName();
        User user = userService.loadUserByUsername(username);
        return user;
    }

    @PostMapping("/rent/{carId}")
    public String processRent(@Valid @ModelAttribute("rentOrder") RentOrder rentOrder,
                              BindingResult result,
                              @Valid @ModelAttribute("passport") Passport passport,
                              @Valid @ModelAttribute("car") Car car,
                              @ModelAttribute("user") User user,
//                              RedirectAttributes redirectAttrs,
                              Model model) {
        if (result.hasErrors()) {
//            redirectAttrs.addAttribute("id", car.getId());
            return "/rent/"+car.getId();
        }
        rentOrderService.processOrder(rentOrder, passport, car, user);
        return "redirect:/car";
    }
}
