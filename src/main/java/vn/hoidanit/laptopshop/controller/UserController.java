package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.service.UserSevice;

@Controller
public class UserController {
    private UserSevice userSevice;

    public UserController(UserSevice userSevice) {
        this.userSevice = userSevice;
    }

    @RequestMapping("/")
    public String getView() {
        return "helloMVC.html";
    }
}

// @RestController
// public class UserController {

// private UserSevice userService;

// public UserController(UserSevice userService) {
// this.userService = userService;
// }

// @GetMapping("/")
// public String getHomePage() {
// return this.userService.helloService();
// }

// }
