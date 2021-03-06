package com.tandcode.final_project.car_rental.entity;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

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

    @DecimalMin(value = "1", message = "min")
    @DecimalMax(value = "90", message = "max")
    Integer days;

    Boolean withDriver;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="car_id")
    Car car;

    @ManyToOne
    @JoinColumn(name="passport_id")
    @Valid
    Passport passport;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    String rejectReason;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    BigDecimal repairPrice;

    String repairDescription;

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
