package com.tandcode.final_project.car_rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/management")
public class ManagerController {
    @GetMapping
    private String orderList(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("carBrandFilter") Optional<String> filteringCarBrand,
                           @RequestParam("qualityClassFilter") Optional<String> filteringQualityClass,
                           @RequestParam("sortField") Optional<String> sortingField,
                           @RequestParam("sortDir") Optional<String> sortingDir
    ){
        return "manager-order-list";
    }
}
