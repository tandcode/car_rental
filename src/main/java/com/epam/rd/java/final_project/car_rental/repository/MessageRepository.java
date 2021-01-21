package com.epam.rd.java.final_project.car_rental.repository;

import com.epam.rd.java.final_project.car_rental.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);
}
