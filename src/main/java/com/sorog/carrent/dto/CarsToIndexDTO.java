package com.sorog.carrent.dto;

import com.sorog.carrent.model.CarTypes;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Data
@Getter
@Setter
public class CarsToIndexDTO {
    private Long car_id;
    private String carName;
    private BigDecimal costPerDay;
    private CarTypes type;
    private String carImage;
}
