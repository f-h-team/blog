package com.chao.service.impl;

import com.chao.dto.Result;
import com.chao.service.UserService;
import com.chao.utils.ThreadLocalUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;
   @BeforeAll
   static void setUpBeforeClass() throws Exception {
       ThreadLocalUtils.set(3);
   }
    @Test
    void register() {
    }

    @Test
    void login() {
        Result wsoal = userService.login("wsoal", "123");
        System.out.println(wsoal);
    }

    @Test
    void showInfo() {
    }

    @Test
    void update() {
        System.out.println(userService.update("hh", "hh"));
    }

    @Test
    void updatePassword() {
    }
}