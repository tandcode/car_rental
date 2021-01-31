package com.tandcode.final_project.car_rental.repository;

import com.tandcode.final_project.car_rental.entity.RentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentOrderRepository extends JpaRepository<RentOrder, Long> {

}
