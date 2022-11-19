package com.sorog.carrent.dto;

import com.sorog.carrent.model.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AdminUserUpdateDTO {
    private Long userId;
    private Role newRole;
    private boolean newActivity;
}
