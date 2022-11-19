package com.sorog.carrent.dto;

import com.sorog.carrent.model.CarTypes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Setter
@Getter
public class CarUpdateDTO {
    public Long carId;
    public String carName;
    public CarTypes type;
    public BigDecimal costPerDay;
}
