package vn.ndkien.laptopshop.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.domain.auth.Register;
import vn.ndkien.laptopshop.service.ProductService;
import vn.ndkien.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        List<Product> products = this.productService.getAllProduct();
        model.addAttribute("product1", products);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("registerUser", new Register());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") Register register) {
        User user = this.userService.registerDTOtoUser(register);
        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        // Đặt lại password sau khi xử lí

        user.setPassword(hashPassword);

        // Save Role_id: Lưu dưới dạng Id
        // ndkien.getRole():trả ra đối tượng Role
        // .getName(): Từ đối tượng đã tìm ra, lấy hàm getName
        user.setRole(this.userService.getRoleByName("USER"));

        this.userService.handleSaveUser(user);
        return "redirect:/login";

    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {

        return "client/auth/login";
    }

}
