package vn.ndkien.laptopshop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.repository.UserRepository;
import vn.ndkien.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Trang chủ
    @RequestMapping("/")
    public String getHomePage(Model model) {
        // 3. Lấy tất cả danh sách người dùng có email là ndke@gmail.com
        // List<User> listUserEmail =this.userService.getAllUserEmail("ndke@gmail.com");
        // 2. Lấy tất cả danh sách trong database
        // List<User> users = this.userService.getAllUser(null);
        model.addAttribute("null", "kien");
        return "hello";
    }

    // 2. Lấy danh sách tất cả người dùng
    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUser(null);
        // Truyền data qua view trong đó:
        // users :giá trị gắn vào
        // users1: giá trị nhận được bên View
        model.addAttribute("users1", users);
        return "admin/user/table-user";
    }

    // 3. Xem chi tiết thông tin 1 người dùng
    @RequestMapping("/admin/user/{id}") // Get
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getOneUserId(id);
        // kien : Giá trị nhận được bên View
        // user1 : Giá trị gắn vào
        model.addAttribute("kien", user);
        model.addAttribute("id", id);
        return "admin/user/show";
    }

    // Form tạo người dùng
    @RequestMapping("/admin/user/create") // Get
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        // Lấy từ file create.jsp trong user folder admin
        return "admin/user/create";
    }

    // 1. Lưu dữ liệu người dùng khi nhập form
    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    // @ModelAttribute: Lấy giá trị từ View
    public String createUserPage(Model model, @ModelAttribute("newUser") User ndkien) {
        // this.userService.handleSaveUser(ndkien);
        // redirect nghĩa là sau khi đã lưu db thì sẽ vào đường link/admin/user
        return "redirect:/admin/user";
    }

    // Lấy thông tin người dùng cần sửa
    @RequestMapping(value = "/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getOneUserId(id);
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";

    }

    // Định nghĩa action bên view
    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User ndkien) {
        User currentUser = this.userService.getOneUserId(ndkien.getId());
        if (currentUser != null) {
            currentUser.setAddress(ndkien.getAddress());
            currentUser.setFullname(ndkien.getFullname());
            currentUser.setPhone(ndkien.getPhone());

            this.userService.handleSaveUser(currentUser);
        }
        return "redirect:/admin/user";

    }

    @GetMapping("admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("newUser", new User());
        model.addAttribute("id", id);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User kien) {
        this.userService.deleteUserId(kien.getId());
        // Vì dùng hàm void trong Service
        return "redirect:/admin/user";
    }
}
