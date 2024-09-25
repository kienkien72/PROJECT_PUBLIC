package vn.hoidanit.laptopshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.service.UserSevice;

@RestController
public class UserController {

    private UserSevice userService;

    public UserController(UserSevice userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage() {
        return this.userService.helloService();
    }

}
