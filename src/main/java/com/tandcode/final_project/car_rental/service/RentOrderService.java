package com.tandcode.final_project.car_rental.service;

import com.tandcode.final_project.car_rental.entity.Car;
import com.tandcode.final_project.car_rental.entity.Passport;
import com.tandcode.final_project.car_rental.entity.RentOrder;
import com.tandcode.final_project.car_rental.entity.User;
import com.tandcode.final_project.car_rental.repository.CarRepository;
import com.tandcode.final_project.car_rental.repository.PassportRepository;
import com.tandcode.final_project.car_rental.repository.RentOrderRepository;
import com.tandcode.final_project.car_rental.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RentOrderService {

    @Autowired
    RentOrderRepository rentOrderRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    PassportRepository passportRepository;

    @Autowired
    UserRepository userRepository;

    public List<RentOrder> findAll() {
        return rentOrderRepository.findAll();
    }

    public List<RentOrder> findAllByUser(User user) {
        return rentOrderRepository.findAllByUser(user);
    }

    public RentOrder findById(Long id) {
        return rentOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rent order id: " + id));
    }

    @Transactional
    public boolean createOrder(RentOrder rentOrder,
                               Boolean submitNew,
                               RedirectAttributes redirectAttrs,
                               Model model) {
        rentOrder.setOrderStatus(submitNew ? RentOrder.OrderStatus.UNPAID : RentOrder.OrderStatus.REJECTED);
        rentOrderRepository.save(rentOrder);
        if(!submitNew){
            model.addAttribute("rentOrder", rentOrder);
            redirectAttrs.addAttribute("id", rentOrder.getId());
            return true;
        }

        rentOrder.getCar().setIsInUsage(true);
        carRepository.save(rentOrder.getCar());
        return false;
    }

    public void save(RentOrder rentOrder) {
        rentOrderRepository.save(rentOrder);
    }

    public void saveWithStatus(RentOrder rentOrder, RentOrder.OrderStatus orderStatus) {
        rentOrder.setOrderStatus(orderStatus);
        rentOrderRepository.save(rentOrder);
    }

    public void releaseCar(RentOrder rentOrder) {
        if(rentOrder.getCar().getIsInUsage()){
            rentOrder.getCar().setIsInUsage(false);
            carRepository.save(rentOrder.getCar());
        }
    }

    public void saveClosed(RentOrder rentOrder) {
        saveWithStatus(rentOrder, RentOrder.OrderStatus.CLOSED);
        releaseCar(rentOrder);
    }

    public void findAllOrdersForPrincipal(Principal principal, Model model) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        model.addAttribute("orders", findAllByUser(user));
    }

    @Transactional
    public void processOrder(RentOrder rentOrder, User user) {
        Passport passport = rentOrder.getPassport();
        Car car = rentOrder.getCar();
        Passport passportFromRepository = passportRepository.findByPassportNumber(passport.getPassportNumber())
                .orElse(null);
        if(passport.equals(passportFromRepository)){
            passport = passportFromRepository;
        }

        if(!passport.getUsers().contains(user))passport.getUsers().add(user);
        passportRepository.save(passport);

        rentOrder.setPassport(passport);
        rentOrder.setUser(user);
        rentOrder.setCar(car);
        rentOrder.setOrderStatus(RentOrder.OrderStatus.NEW);

        log.info("Creating rent order: " + rentOrder);
        rentOrderRepository.save(rentOrder);

    }
}
