package com.tandcode.final_project.car_rental.controller;

import com.tandcode.final_project.car_rental.service.RentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class OrdersController {

    @Autowired
    RentOrderService rentOrderService;

    @GetMapping("/management/orders")
    private String managerOrderList(Model model){
        model.addAttribute("orders", rentOrderService.findAll());
        return "manager-order-list";
    }

    @GetMapping("/orders")
    private String userOrderList(Principal principal,
            Model model){
        rentOrderService.findAllOrdersForPrincipal(principal, model);
        return "user-order-list";
    }


}
