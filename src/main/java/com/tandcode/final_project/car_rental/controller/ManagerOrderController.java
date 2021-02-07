package com.tandcode.final_project.car_rental.controller;

import com.tandcode.final_project.car_rental.entity.RentOrder;
import com.tandcode.final_project.car_rental.service.RentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/management/order")
public class ManagerOrderController {

    @Autowired
    RentOrderService rentOrderService;

    @ModelAttribute
    public void addOrder(@PathVariable Long id, Model model){
        model.addAttribute("rentOrder", rentOrderService.findById(id));
    }

    @PostMapping("/{id}")
    private String newOrder(@PathVariable Long id,
                            @Valid @ModelAttribute("rentOrder") RentOrder rentOrder,
                            @RequestParam Boolean submitNew,
                            RedirectAttributes redirectAttrs,
                            Model model
    ){
        if (rentOrderService.createOrder(rentOrder, submitNew, redirectAttrs, model)) {
            return "redirect:/management/order/{id}/reject";
        }

        return "redirect:/management/orders";
    }

    @GetMapping("/{id}/reject")
    private String handleReject(@PathVariable Long id,
                                @Valid @ModelAttribute("rentOrder") RentOrder rentOrder,
                                Model model
    ){
        return "manager-order-reject";
    }

    @PostMapping("/{id}/reject")
    private String saveReject(@PathVariable Long id,
                              @Valid @ModelAttribute("rentOrder") RentOrder rentOrder,
                              BindingResult result,
                              Model model
    ){
        if (result.hasErrors()) {
            return "manager-order-reject";
        }

        rentOrderService.save(rentOrder);
        return "redirect:/management/orders";
    }


    @PostMapping("/{id}/paid")
    private String paidOrder(@PathVariable Long id,
                             @Valid @ModelAttribute("rentOrder") RentOrder rentOrder
    ){
        rentOrderService.saveWithStatus(rentOrder, RentOrder.OrderStatus.PAID);
        return "redirect:/management/orders";
    }

    @PostMapping("/{id}/closed")
    private String closedOrder(@PathVariable Long id,
                               @Valid @ModelAttribute("rentOrder") RentOrder rentOrder
    ){
        rentOrderService.saveClosed(rentOrder);
        return "redirect:/management/orders";
    }

    @PostMapping("/{id}/in-usage")
    private String inUsageOrder(@PathVariable Long id,
                                @Valid @ModelAttribute("rentOrder") RentOrder rentOrder

    ){
        rentOrderService.saveWithStatus(rentOrder, RentOrder.OrderStatus.IN_USAGE);
        return "redirect:/management/orders";
    }

    @PostMapping("/{id}/repair")
    private String unpaidRepair(@PathVariable Long id,
                                @Valid @ModelAttribute("rentOrder") RentOrder rentOrder,
                                RedirectAttributes redirectAttrs
    ){
        rentOrderService.saveWithStatus(rentOrder, RentOrder.OrderStatus.UNPAID_REPAIR);
        redirectAttrs.addAttribute("id", rentOrder.getId());
        return "redirect:/management/order/{id}/repair-payment";
    }

    @GetMapping("/{id}/repair-payment")
    private String handleRepair(@PathVariable Long id,
                                @Valid @ModelAttribute("rentOrder") RentOrder rentOrder,
                                Model model
    ){
        return "manager-order-repair";
    }

    @PostMapping("/{id}/repair-payment")
    private String saveRepair(@PathVariable Long id,
                              @Valid @ModelAttribute("rentOrder") RentOrder rentOrder,
                              BindingResult result,
                              Model model
    ){
        if (result.hasErrors()) {
            return "manager-order-repair";
        }

        rentOrderService.save(rentOrder);
        return "redirect:/management/orders";
    }
}
