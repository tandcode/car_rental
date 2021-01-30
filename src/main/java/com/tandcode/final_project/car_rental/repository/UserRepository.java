package com.tandcode.final_project.car_rental.repository;

import com.tandcode.final_project.car_rental.entity.User;
import com.sun.istack.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(@NotNull String username);

}
