package com.tandcode.final_project.car_rental.controller;

import com.tandcode.final_project.car_rental.entity.Role;
import com.tandcode.final_project.car_rental.entity.User;
import com.tandcode.final_project.car_rental.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute(user);
        return "user-edit";
    }
    //TODO user edition finish
    @PostMapping
    public String userSave(
            @RequestParam("userId") User user,
            @RequestParam String username,
            @RequestParam(required = false) String manager
    ) {
        user.setUsername(username);

        user.getRoles().clear();
        user.getRoles().add(manager != null ? Role.MANAGER : Role.USER);

        log.info("Creating user: " + user);
        userRepository.save(user);
        return "redirect:/user";
    }
}
