package com.sorog.carrent.controllers;

import com.sorog.carrent.dto.CarDTOAll;
import com.sorog.carrent.dto.CarUpdateDTO;
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

    @PostMapping("/admin/updateCar")
    public ResponseEntity<?> updateCar(@RequestBody CarUpdateDTO newCar) throws ControllerException {
        try {
            Car car = carService.getCarById(newCar.getCarId());
            car.setCarName(newCar.getCarName());
            car.setType(newCar.getType());
            car.setCostPerDay(newCar.getCostPerDay());
            carService.save(car);

            logger.debug("update car: " + car.getCarName() + " id: " + car.getCar_id().toString());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @PostMapping(value = "/admin/addCar")
    public ResponseEntity<?> addCar(@RequestBody CarDTOAll carToAdd) throws IOException, ControllerException {

       /* Tested: Check picture after download

        try{
            File image = new File("src\\main\\resources\\static\\img\\" + carToAdd.CarImage.getName());
            if(image.exists()){
                image = new File("src\\main\\resources\\static\\img\\"+ UUID.randomUUID().toString()+carToAdd.CarImage.getName());
            }
            image.createNewFile();

            FileOutputStream output = new FileOutputStream(image.getAbsolutePath());

            output.write(Base64.getDecoder().decode(carToAdd.getCarImage().getBase64().getBytes()));

            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            Car car = Car.builder()
                    .carName(carToAdd.getCarName())
                    .costPerDay(carToAdd.getCostPerDay())
                    .type(carToAdd.getType())
                    .carImage(Base64.getDecoder().decode(carToAdd.getCarImage().getBase64().getBytes()))
                    .build();
            carService.save(car);

            logger.debug("add car: " + car.toString());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/admin/deleteCar/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable(name = "id") Long Id) throws ControllerException {
        try {
            carService.deleteCar(Id);

            logger.debug("delete car: " + Id.toString());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException(e.getMessage(), e);
        }
    }


}
