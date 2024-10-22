package vn.ndkien.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.service.ProductService;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/product/create") // Get
    public String getCreateUserPage(Model model) {
        model.addAttribute("newProduct", new Product());
        // Lấy từ file create.jsp trong user folder admin
        return "admin/product/create";
    }

    // Lưu sản phẩm
    @PostMapping("/admin/product/create")
    public String createProductPage(Model model,
            @ModelAttribute("newProduct") Product product) {
        this.productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    // Lấy tất cả sản phẩm
    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.getAllProduct(null);
        model.addAttribute("product1", products);
        return "admin/product/table-product";
    }

}
