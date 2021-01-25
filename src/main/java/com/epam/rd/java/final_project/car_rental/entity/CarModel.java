package com.epam.rd.java.final_project.car_rental.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "model")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String name;

    @OneToMany(mappedBy = "carModel")
    List<Car> carList;
}
