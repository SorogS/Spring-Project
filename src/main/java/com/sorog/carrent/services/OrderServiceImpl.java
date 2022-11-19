package com.sorog.carrent.services;

import com.sorog.carrent.dto.MakeOrderDTO;
import com.sorog.carrent.exception.ControllerException;
import com.sorog.carrent.model.Car;
import com.sorog.carrent.model.Order;
import com.sorog.carrent.model.Order_Status;
import com.sorog.carrent.model.User;
import com.sorog.carrent.repository.CarRepository;
import com.sorog.carrent.repository.OrderRepository;
import com.sorog.carrent.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.rowset.serial.SerialException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public boolean deleteOrder(Long orderId) {

        Order order = orderRepository.findById(orderId).get();
        orderRepository.delete(order);
        return true;
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long OrderId) {
        return orderRepository.findById(OrderId);
    }

    @Override
    public Order createOrder(MakeOrderDTO makeOrderDTO, String UserLogin) throws SerialException, ControllerException {

        User user = userRepository.findByLogin(UserLogin);
        Car car = carRepository.getById(makeOrderDTO.carId);
        Order order = Order.builder()
                .car(car)
                .user(user)
                .dateEnd(Date.valueOf(makeOrderDTO.dateRentEnd))
                .dateStart(Date.valueOf(makeOrderDTO.dateRentStart))
                .sumrentcost(makeOrderDTO.sumRentCost)
                .status(Order_Status.Active)
                .build();
        System.out.println(order);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> userOrders(String UserLogin) throws SerialException, ControllerException {
        User user = userRepository.findByLogin(UserLogin);
        List<Order> orders = orderRepository.findAllByUser(user);
        return orders;
    }

}
