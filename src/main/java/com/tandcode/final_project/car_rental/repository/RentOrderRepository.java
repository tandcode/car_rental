package com.tandcode.final_project.car_rental.repository;

import com.tandcode.final_project.car_rental.entity.RentOrder;
import com.tandcode.final_project.car_rental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentOrderRepository extends JpaRepository<RentOrder, Long> {
    List<RentOrder> findAllByUser(User user);
}
