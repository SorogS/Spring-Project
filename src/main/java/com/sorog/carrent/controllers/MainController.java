package com.sorog.carrent.controllers;

import com.sorog.carrent.dto.*;
import com.sorog.carrent.dto.*;
import com.sorog.carrent.exception.ControllerException;
import com.sorog.carrent.jwt.JwtProvider;
import com.sorog.carrent.model.*;
import com.sorog.carrent.model.*;
import com.sorog.carrent.services.CarService;
import com.sorog.carrent.services.MailSender;
import com.sorog.carrent.services.OrderService;
import com.sorog.carrent.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Validated
@RestController
public class MainController {


    @Autowired
    private MailSender mailSender;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private OrderService orderService;
    @Autowired
    private static CarService carService;

    private static final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    public MainController(MailSender mailSender, OrderService orderService, UserService userService, CarService carService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.carService = carService;
        this.mailSender = mailSender;
        this.orderService = orderService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) throws ControllerException {
        try {

            User user = userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
            if (user != null) {
                if (user.isActive()) {
                    String token = jwtProvider.generateToken(user.getLogin());
                    AuthResponse response = new AuthResponse(token, user.getUserRole().getName());
                    System.out.println(user.getUserRole().getName());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Verify your account", HttpStatus.FORBIDDEN);
                }

            } else {

                throw new ControllerException("not such user");

            }
        } catch (ControllerException e) {

            logger.error(e.getMessage());
            throw new ControllerException("auth", e);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) throws ControllerException {
        try {
            if (!userService.existsUserByLogin(registrationRequest.getLogin())) {
                User user = new User();
                user.setPassword(registrationRequest.getPassword());
                user.setLogin(registrationRequest.getLogin());
                user.setEmail(registrationRequest.getEmail());
                user.setSurname(registrationRequest.getSurname());
                user.setName(registrationRequest.getName());
                user.setActivationCode(UUID.randomUUID().toString());
                user.setActive(false);
                userService.saveUser(user);

                if (!user.getEmail().isEmpty()) {
                    String message = String.format("Hello, %s!\n " +
                                    "Welcome Radiator Springs! Please, visit next link: http://localhost:8080/activate/%s",
                            user.getLogin(), user.getActivationCode());
                    mailSender.sendMail(user.getEmail(), "Activation code", message);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FOUND);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException("register error", e);
        }

    }

    @GetMapping("/activate/{code}")
    public ModelAndView activate(Model model, @PathVariable String code) throws ControllerException {

        try {

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException("activation error", e);
        }
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");

        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = {"/", ""}, produces = MediaType.IMAGE_JPEG_VALUE)
    public ModelAndView indexPage(Model model) throws ControllerException {
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("index");
            List<Car> cars = carService.getAll();
            List<CarTypes> carTypes = Arrays.asList(CarTypes.values());
            List<CarsToIndexDTO> carsToUpload = new ArrayList<>();
            for (Car car : cars) {

                carsToUpload.add(CarsToIndexDTO.builder()
                        .carName(car.getCarName())
                        .costPerDay(car.getCostPerDay())
                        .type(car.getType())
                        .car_id(car.getCar_id())
                        .carImage(Base64.getEncoder().encodeToString(car.getCarImage()))
                        .build());
            }
            model.addAttribute("carTypes", carTypes);
            model.addAttribute("cars", carsToUpload);
            return modelAndView;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ControllerException("Index error", e);
        }

    }


    @GetMapping("/admin/adminpage/{tableName}")
    public ModelAndView adminPage(Model model, @PathVariable("tableName") String tableName) throws ControllerException {
        try {
            ModelAndView modelAndView = new ModelAndView("adminpage");
            List<CarTypes> types = Arrays.asList(CarTypes.values());
            List<User> users = null;
            List<Car> cars = null;
            List<Order> orders = null;
            List<Order_Status> statuses = Arrays.asList(Order_Status.values());
            users = userService.findAll();
            cars = carService.getAll();
            orders = orderService.getAllOrders();
            List<CarsToIndexDTO> carsToUpload = new ArrayList<>();
            for (Car car : cars) {

                carsToUpload.add(CarsToIndexDTO.builder()
                        .carName(car.getCarName())
                        .costPerDay(car.getCostPerDay())
                        .type(car.getType())
                        .car_id(car.getCar_id())
                        .carImage(Base64.getEncoder().encodeToString(car.getCarImage()))
                        .build());
            }
            AdminTablesDTO tables = new AdminTablesDTO();
            tables.setAllCars(carsToUpload);
            tables.setAllOrder(orders);
            tables.setAllUsers(users);
            tables.setType(types);
            tables.setStatuses(statuses);
            tables.setTableName(tableName);
            model.addAttribute("tables", tables);

            logger.debug("admin page opened");

            return modelAndView;
        } catch (Exception e) {
            logger.error("error while try to open admin page " + e.getMessage());
            throw new ControllerException("openAdminPAge", e);
        }


    }


    //TODO:validated
    //TODO:Log4j
    //TODO:JUnit


}
