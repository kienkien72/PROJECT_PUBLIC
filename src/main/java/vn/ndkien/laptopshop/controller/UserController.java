package vn.ndkien.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

import vn.ndkien.laptopshop.service.UserService;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.helloService();
        model.addAttribute("kien", test);
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String userInfo(Model model) {
        return "admin/user/create";
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
