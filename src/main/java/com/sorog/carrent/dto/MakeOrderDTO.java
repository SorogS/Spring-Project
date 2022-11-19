package com.sorog.carrent.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class MakeOrderDTO {

    public Long carId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate dateRentStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate dateRentEnd;

    public BigDecimal sumRentCost;

}
