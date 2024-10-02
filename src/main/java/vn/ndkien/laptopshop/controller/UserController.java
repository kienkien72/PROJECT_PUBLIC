package vn.ndkien.laptopshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        // 3. Lấy tất cả danh sách người dùng có email là ndke@gmail.com
        // List<User> listUserEmail =this.userService.getAllUserEmail("ndke@gmail.com");
        // 2. Lấy tất cả danh sách trong database
        // List<User> users = this.userService.getAllUser(null);
        model.addAttribute("null", "kien");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        // Lấy tất cả dữ liệu người dùng
        List<User> users = this.userService.getAllUser(null);
        // Truyền data qua view trong đó:
        // users :giá trị gắn vào
        // users1: giá trị nhận được bên View
        model.addAttribute("users1", users);
        return "admin/user/table-user";
    }

    @RequestMapping("/admin/user/{id}") // Get
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getOneUserId(id);
        // kien : Giá trị nhận được bên View
        // user1 : Giá trị gắn vào
        model.addAttribute("kien", user);
        model.addAttribute("id", id);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/create") // Get
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        // Lấy từ file create.jsp trong user folder admin
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User ndkien) {

        // 1. Gọi hàm service để lưu vào db
        this.userService.handleUser(ndkien);
        // redirect nghĩa là sau khi đã lưu db thì sẽ vào đường link/admin/user
        return "redirect:/admin/user";
    }
}
