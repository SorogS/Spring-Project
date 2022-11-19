package com.sorog.carrent.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class RegistrationRequest {

    private String name;

    private String surname;

    private String login;

    private String password;

    private String email;


}
