package com.sorog.carrent.dto;

import com.sorog.carrent.model.CarTypes;
import com.sorog.carrent.model.Order_Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrdersDTO {
    public Long orderId;
    public String carName;
    public CarTypes carType;
    public LocalDate dateRentStart;
    public LocalDate dateRentEnd;
    public BigDecimal costPerDay;
    public BigDecimal sumRentCost;
    public Order_Status status;
}
