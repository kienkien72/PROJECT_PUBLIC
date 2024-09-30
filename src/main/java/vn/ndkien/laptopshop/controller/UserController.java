package vn.ndkien.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.repository.UserRepository;
import vn.ndkien.laptopshop.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        // 2. Lấy tất cả danh sách trong database
        List<User> arrUser = this.userService.getAllUser(null);

        // 3. Lấy tất cả danh sách người dùng có email là ndke@gmail.com
        List<User> listUserEmail = this.userService.getAllUserEmail("ndke@gmail.com");
        System.out.println(listUserEmail);
        model.addAttribute("kien", "test");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User());
        // Lấy từ file create.jsp trong user folder admin
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User ndkien) {
        System.out.println("kien" + ndkien);
        // 1. Gọi hàm service để lưu vào db
        this.userService.handleUser(ndkien);
        return "hello";
    }
}
