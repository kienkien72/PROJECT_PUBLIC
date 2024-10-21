package vn.ndkien.laptopshop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.User;

@Controller
public class ProductController {

    @GetMapping("/admin/product")
    public String getProductPage() {
        return "admin/product/show";
    }

    @RequestMapping("/admin/product/create") // Get
    public String getCreateUserPage(Model model) {
        model.addAttribute("newProduct", new Product());
        // Lấy từ file create.jsp trong user folder admin
        return "admin/product/create";
    }

}
