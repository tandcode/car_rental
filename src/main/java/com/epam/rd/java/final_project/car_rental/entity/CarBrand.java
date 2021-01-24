package com.epam.rd.java.final_project.car_rental.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "brand")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String name;

    @OneToMany
    List<Car> carList;
}
