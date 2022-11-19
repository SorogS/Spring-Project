package com.sorog.carrent.services;


import com.sorog.carrent.model.*;
import com.sorog.carrent.repository.OrderRepository;
import com.sorog.carrent.repository.UserRepository;

import com.sorog.carrent.repository.UserRoleRepository;
import com.sorog.carrent.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private OrderRepository orderRepository;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }


    public User saveUser(User user) {
        UserRole userRole = userRoleRepository.findByName(Role.ROLE_USER);
        if (user.getUserRole() == null) {
            user.setUserRole(userRole);
            user.setBalance(BigDecimal.valueOf(0));
        }
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public boolean existsUserByLogin(String login) {
        return userRepository.existsUserByLogin(login);
    }

    public boolean existsUserByLoginAndPassword(String login, String password) {
        return findByLoginAndPassword(login, password) != null;
    }

    @Override
    public User findById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public UserRole getRoleByName(Role role) {
        return userRoleRepository.findByName(role);
    }

    @Override
    public void deleteUser(Long Id) {
        User user = userRepository.getById(Id);
        List<Order> orders = orderRepository.findAllByUser(user);
        orderRepository.deleteAll(orders);
        userRepository.deleteById(Id);
    }

    @Override
    public boolean updateUserAfterRentEnd(Order order) {
        if (order.getStatus() == Order_Status.Rent_End) {
            User user = userRepository.getById(order.getUser().getUser_id());
            if (user.getBalance().compareTo(order.getSumrentcost()) == 1) {
                user.setBalance(user.getBalance().subtract(order.getSumrentcost()));

                userRepository.save(user);

                return true;

            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateUserAfterRentEndAdmin(Order order) {
        User user = userRepository.getById(order.getUser().getUser_id());
        user.setBalance(user.getBalance().subtract(order.getSumrentcost()));
        userRepository.save(user);
    }

    @Override
    public boolean activateUser(String code) {

        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            user.setActive(false);
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }

}
