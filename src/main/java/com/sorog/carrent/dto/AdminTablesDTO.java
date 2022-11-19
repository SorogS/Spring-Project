package com.sorog.carrent.dto;

import com.sorog.carrent.model.*;
import com.sorog.carrent.model.CarTypes;
import com.sorog.carrent.model.Order;
import com.sorog.carrent.model.Order_Status;
import com.sorog.carrent.model.User;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
public class AdminTablesDTO {
    public List<Order> allOrder;
    public List<CarsToIndexDTO> allCars;
    public List<User> allUsers;
    public List<CarTypes> type;
    public List<Order_Status> statuses;
    public String tableName;
}
