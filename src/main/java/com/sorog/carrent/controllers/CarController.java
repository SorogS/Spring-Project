package com.sorog.carrent.controllers;


import com.sorog.carrent.exception.ControllerException;
import com.sorog.carrent.model.Car;
import com.sorog.carrent.services.CarService;
import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
public class CarController {
    private static List<Car> cars = new ArrayList<Car>();

    @Autowired
    private static CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    private static final Logger logger = Logger.getLogger(CarController.class);

    @GetMapping("/getrentcar/{id}") //get car for rent by id
    public ResponseEntity<?> rentCar(@PathVariable(name = "id") Long id) throws ControllerException {
        Car car = null;
        try {
            car = carService.getCarById(id);

            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            throw new ControllerException(e);
        }

    }






}
