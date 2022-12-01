package com.sorog.carrent;

import com.sorog.carrent.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void existsUserByLogin() {
        try {
            Assert.assertTrue(userService.existsUserByLogin("Admin"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void existsUserByLoginAndPassword() {
        try {
            Assert.assertTrue(userService.existsUserByLoginAndPassword("Admin", "Admin"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
