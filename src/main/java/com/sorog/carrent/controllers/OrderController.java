package com.sorog.carrent.controllers;

import com.sorog.carrent.dto.MakeOrderDTO;
import com.sorog.carrent.dto.RentUpdateDTO;
import com.sorog.carrent.exception.ControllerException;
import com.sorog.carrent.jwt.JwtFilter;
import com.sorog.carrent.model.Order;
import com.sorog.carrent.model.Order_Status;
import com.sorog.carrent.services.OrderService;
import com.sorog.carrent.services.UserService;
import com.sorog.carrent.validator.RentValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;

@RestController
public class OrderController {

    @Autowired
    private RentValidator rentValidator;

    @Autowired
    private static OrderService orderService;

    @Autowired
    private static UserService userService;

    public OrderController(UserService userService, OrderService orderService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    private static final Logger logger = Logger.getLogger(OrderController.class);

    //TODO:Date Validation
    @PostMapping("/makeorder")
    public ResponseEntity<?> makeOrderForRent(@RequestBody @Validated MakeOrderDTO makeOrderDTO, BindingResult bindingResult) throws ControllerException, SerialException {

        try {

            rentValidator.validate(makeOrderDTO, bindingResult);
            if (bindingResult.hasErrors()) throw new ControllerException("not correct data");

            orderService.createOrder(makeOrderDTO, JwtFilter.getCurrentUserLogin());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException(e);
        }
    }

    @PutMapping("/user/updateOrder")
    public ResponseEntity<?> updateUserOrder(@RequestBody RentUpdateDTO rentUpdateDTO) throws ControllerException {

        try {
            Order order = orderService.findById(rentUpdateDTO.orderId).get();
            if (rentUpdateDTO.newStatus != null && rentUpdateDTO.newStatus != Order_Status.Deleted) {
                order.setStatus(rentUpdateDTO.newStatus);
                order.setSumrentcost(rentUpdateDTO.getNewRentCost());
                order.setDateEnd(Date.valueOf(rentUpdateDTO.getNewRentDateEnd()));
                if (rentUpdateDTO.newStatus != Order_Status.Rent_End_Before_Start) {
                    if (!userService.updateUserAfterRentEnd(order)) {
                        return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
                    }
                }
            } else {
                if (rentUpdateDTO.newStatus == Order_Status.Deleted) {
                    order.setStatus(rentUpdateDTO.newStatus);
                } else {
                    order.setDateEnd(Date.valueOf(rentUpdateDTO.newRentDateEnd));
                    order.setSumrentcost(rentUpdateDTO.getNewRentCost());
                }
            }

            orderService.updateOrder(order);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException(e);
        }
    }

    @GetMapping("/admin/endRent/{orderId}")
    public ResponseEntity<?> endRentByAdmin(@PathVariable(name = "orderId") Long orderId) throws ControllerException {

        try {
            Order order = orderService.findById(orderId).get();
            SimpleDateFormat formatter = new SimpleDateFormat("YYYY-mm-dd");

            Date dateToday = new Date(System.currentTimeMillis());

            long today = System.currentTimeMillis();
            long notToday = order.getDateStart().getTime();

            if (dateToday.getTime() > order.getDateStart().getTime() || dateToday.getTime() == order.getDateStart().getTime()) {
                long timeDiff = dateToday.getTime() - order.getDateStart().getTime();
                long dayDiff = timeDiff / (1000 * 3600 * 24);

                order.setSumrentcost(dayDiff == 0 ? order.getCar().getCostPerDay() : BigDecimal.valueOf(dayDiff).multiply(order.getCar().getCostPerDay()));
                order.setStatus(Order_Status.Rent_End);

                userService.updateUserAfterRentEndAdmin(order);

            } else {
                order.setStatus(Order_Status.Rent_End_Before_Start);
                order.setSumrentcost(BigDecimal.valueOf(0));
            }

            logger.debug("Admin update order: " + order.getOrder_id());

            orderService.updateOrder(order);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException(e);
        }

    }

    @DeleteMapping("/admin/deleteOrder/{id}")
    public ResponseEntity<?> deleteUserOrder(@PathVariable(name = "id") Long id) throws ControllerException {

        try {
            orderService.deleteOrder(id);

            logger.debug("Admin delete order: " + id.toString());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException(e);
        }

    }


}
