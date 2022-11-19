package com.sorog.carrent.services;

import com.sorog.carrent.dto.MakeOrderDTO;
import com.sorog.carrent.model.Order;
import com.sorog.carrent.exception.ControllerException;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    List<Order> getAllOrders();

    boolean deleteOrder(Long orderId);

    Order updateOrder(Order order);

    Optional<Order> findById(Long OrderId);

    Order createOrder(MakeOrderDTO makeOrderDTO, String UserLogin) throws SerialException, ControllerException;

    List<Order> userOrders(String UserLogin) throws SerialException, ControllerException;
}
