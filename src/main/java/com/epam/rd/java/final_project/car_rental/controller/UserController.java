package com.epam.rd.java.final_project.car_rental.controller;

import com.epam.rd.java.final_project.car_rental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    private String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "user-list";
    }
}
