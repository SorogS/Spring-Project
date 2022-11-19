package com.sorog.carrent.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class UserUpdateDTO {
    public BigDecimal newBalance;
}
