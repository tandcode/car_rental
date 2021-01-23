package com.epam.rd.java.final_project.car_rental.repository;

import com.epam.rd.java.final_project.car_rental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
