package vn.ndkien.laptopshop.controller.client;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.ndkien.laptopshop.domain.Cart;
import vn.ndkien.laptopshop.domain.CartDetail;
import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    // Xem chi tiết một sản phẩm
    @GetMapping("/product/{id}")

    public String getProductPage(Model model, @PathVariable long id) {
        Product product = this.productService.fetchProductById(id).get();
        model.addAttribute("product", product);
        model.addAttribute("id", id);
        return "client/product/detail";

    }

    // HttpServletRequest request: Đối tượng này chứa thông tin của yêu cầu HTTP
    // => dùng để truy cập HttpSession và các thông tin yêu cầu khác.
    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // lấy session hiện tại của người dùng nếu tồn tại
        long productId = id;

        String email = (String) session.getAttribute("email"); // Lấy email của người dùng từ session

        this.productService.handleProductToCart(email, productId, session);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User(); // Tạo mới người dùng

        HttpSession session = request.getSession(false); // lấy session hiện tại của người dùng nếu tồn tại
        long id = (long) session.getAttribute("id");// Lấy id của người dùng từ session; ép kiểu về long
        currentUser.setId(id); // Vì mặc định tạo mới người dùng id = null

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetail = cart.getCartDetail(); // Lấy chi tiết sản phẩm

        double totalPrice = 0;
        for (CartDetail cd : cartDetail) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetail);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/show";
    }

}
