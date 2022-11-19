package com.sorog.carrent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileInfoDTO {
    public String login;
    public String name;
    public String surname;
    public String email;
    public String userRole;
    public BigDecimal balance;

}
