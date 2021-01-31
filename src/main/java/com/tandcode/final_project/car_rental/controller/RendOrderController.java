package com.tandcode.final_project.car_rental.controller;

import com.tandcode.final_project.car_rental.entity.*;
import com.tandcode.final_project.car_rental.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/car")
public class RendOrderController {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentOrderRepository rentOrderRepository;

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/rent/{carId}")
    private String carRent(Model model){
        return "car-rent";
    }

    @ModelAttribute
    public void fillModel(@PathVariable Long carId, Model model){
        model.addAttribute("car", carRepository.findById(carId).orElseThrow());
        model.addAttribute("rentOrder", new RentOrder());
        model.addAttribute("passport", new Passport());
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return user;
    }

    //TODO implement unique passport and choosing it if user already have one
    @PostMapping("/rent/{carId}")
    public String proceedRent(@Valid @ModelAttribute("rentOrder") RentOrder rentOrder,
                              @Valid @ModelAttribute("passport") Passport passport,
                              @Valid @ModelAttribute("car") Car car,
                              @ModelAttribute("user") User user,
                              RedirectAttributes redirectAttrs,
                              Errors errors,
                              Model model) {
        if (errors.hasErrors()) {
            redirectAttrs.addAttribute("id", car.getId());
            return "redirect:/rent/{id}";
        }
        passport.setUser(user);

        passportRepository.save(passport);
        rentOrder.setPassport(passport);

        rentOrder.setCar(car);
        log.info("Creating rent order: " + rentOrder);
        rentOrderRepository.save(rentOrder);
        return "redirect:/car";
    }

}
