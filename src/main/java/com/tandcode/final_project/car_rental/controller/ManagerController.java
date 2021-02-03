package com.tandcode.final_project.car_rental.controller;

import com.tandcode.final_project.car_rental.entity.RentOrder;
import com.tandcode.final_project.car_rental.repository.CarRepository;
import com.tandcode.final_project.car_rental.repository.RentOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/management")
public class ManagerController {

    @Autowired
    RentOrderRepository rentOrderRepository;

    @Autowired
    CarRepository carRepository;

    @GetMapping("/order")
    private String orderList(Model model){
        model.addAttribute("orders", rentOrderRepository.findAll());
        return "manager-order-list";
    }

    @PostMapping("/order/{rentOrderId}")
    private String newOrder(
            Model model,
            @PathVariable Long rentOrderId,
            @RequestParam Boolean submitNew
    ){
        //TODO transactional
        RentOrder rentOrder = rentOrderRepository.findById(rentOrderId).orElseThrow();
        rentOrder.setOrderStatus(submitNew ? RentOrder.OrderStatus.UNPAID : RentOrder.OrderStatus.REJECTED);
        rentOrderRepository.save(rentOrder);
        if(!submitNew){
            model.addAttribute("rentOrder", rentOrder);
            //TODO implement the page with message about rejection
            return "redirect:/management/order/reject";
        }

        rentOrder.getCar().setIsInUsage(true);
        carRepository.save(rentOrder.getCar());

        return "redirect:/management/order";
    }

    @GetMapping("/order/{rentOrderId}/reject")
    private String handleReject(
            @ModelAttribute RentOrder rentOrder
    ){
        return "manager-order-reject";
    }

    @PostMapping("/order/{rentOrderId}/paid")
    private String paidOrder(
            @PathVariable Long rentOrderId
    ){
        RentOrder rentOrder = rentOrderRepository.findById(rentOrderId).orElseThrow();
        rentOrder.setOrderStatus(RentOrder.OrderStatus.PAID);


        rentOrderRepository.save(rentOrder);

        return "redirect:/management/order";
    }

    @PostMapping("/order/{rentOrderId}/closed")
    private String closedOrder(
            @PathVariable Long rentOrderId
    ){
        RentOrder rentOrder = rentOrderRepository.findById(rentOrderId).orElseThrow();

        rentOrder.setOrderStatus(RentOrder.OrderStatus.CLOSED);
        if(rentOrder.getCar().getIsInUsage()){
            rentOrder.getCar().setIsInUsage(false);
            carRepository.save(rentOrder.getCar());
        }

        rentOrderRepository.save(rentOrder);

        return "redirect:/management/order";
    }

    @PostMapping("/order/{rentOrderId}/in-usage")
    private String inUsageOrder(
            @PathVariable Long rentOrderId
    ){
        RentOrder rentOrder = rentOrderRepository.findById(rentOrderId).orElseThrow();

        rentOrder.setOrderStatus(RentOrder.OrderStatus.IN_USAGE);
        rentOrderRepository.save(rentOrder);

        return "redirect:/management/order";
    }
}
