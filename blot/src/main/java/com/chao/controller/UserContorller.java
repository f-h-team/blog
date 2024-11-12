package com.chao.controller;

import com.chao.dto.Result;
import com.chao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/user")
public class UserContorller {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }
    @GetMapping
    public Result logout() {
        return Result.success();
    }
    @PostMapping("/register")
    public Result register(@RequestParam String username, @RequestParam String password) {
        return userService.register(username, password);
    }
    @PostMapping("/show")
    public Result getUserInfo( Integer id) {
        return userService.showInfo(id);
    }
    @PatchMapping("/update")
    public  Result update(String email, String avatar){
        return userService.update(email,avatar);
    }
    @PatchMapping("/updatepwd")
    public Result updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword){
        return userService.updatePassword(oldPassword,newPassword);
    }
}
