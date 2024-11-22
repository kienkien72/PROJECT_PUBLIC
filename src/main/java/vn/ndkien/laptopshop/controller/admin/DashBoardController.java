package vn.ndkien.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.ndkien.laptopshop.repository.ProductRepository;
import vn.ndkien.laptopshop.service.OrderService;
import vn.ndkien.laptopshop.service.ProductService;
import vn.ndkien.laptopshop.service.UserService;

@Controller
public class DashBoardController {
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;

    public DashBoardController(UserService userService, ProductService productService,
            OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    // Hiển thị giao diện trên dashboard
    @GetMapping("/admin")
    public String getDashBoard(Model model) {
        model.addAttribute("countUser", this.userService.countUser());
        model.addAttribute("countProduct", this.userService.countProduct());
        model.addAttribute("countOrder", this.userService.countOrder());
        return "admin/dashboard/show";
    }

}
