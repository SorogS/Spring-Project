package com.sorog.carrent.repository;

import com.sorog.carrent.model.Car;
import com.sorog.carrent.model.Order;
import com.sorog.carrent.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    void delete(Order entity);

    Order save(Order order);

    List<Order> findAllByUser(User user);

    List<Order> findAllByCar(Car car);

}
