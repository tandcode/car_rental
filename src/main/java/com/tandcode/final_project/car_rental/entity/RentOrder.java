package com.tandcode.final_project.car_rental.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class RentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DecimalMin(value = "1", message = "Minimum rent duration 1 day")
    @DecimalMax(value = "90", message = "Maximum rent duration 90 days")
    Integer days;

    //TODO implement driver repo?
    Boolean withDriver;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="car_id")
    Car car;

    @ManyToOne
    @JoinColumn(name="passport_id")
    Passport passport;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Override
    public String toString() {
        return "RentOrder{" +
                "id=" + id +
                ", days=" + days +
                ", withDriver=" + withDriver +
                ", car=" + car +
                ", passport=" + passport +
                ", orderStatus=" + orderStatus +
                '}';
    }

    public enum OrderStatus {
        NEW, REJECTED, UNPAID, PAID, IN_USAGE, UNPAID_REPAIR, CLOSED;
    }
}
