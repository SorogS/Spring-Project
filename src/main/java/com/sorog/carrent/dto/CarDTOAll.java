package com.sorog.carrent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sorog.carrent.model.CarTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class CarDTOAll {
    public String carName;
    public CarTypes type;
    public BigDecimal costPerDay;
    public FileDTO CarImage;


}
