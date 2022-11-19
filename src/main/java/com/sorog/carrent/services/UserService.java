package com.sorog.carrent.services;


import com.sorog.carrent.model.Order;
import com.sorog.carrent.model.Role;
import com.sorog.carrent.model.User;
import com.sorog.carrent.model.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService { //security
    User saveUser(User user);

    User findByLogin(String login);

    List<User> findAll();

    User findById(Long id);

    User findByLoginAndPassword(String login, String password);

    boolean existsUserByLogin(String login);

    boolean existsUserByLoginAndPassword(String login, String password);

    UserRole getRoleByName(Role role);

    void deleteUser(Long Id);

    boolean updateUserAfterRentEnd(Order order);

    void updateUserAfterRentEndAdmin(Order order);

    boolean activateUser(String code);

}
