package vn.ndkien.laptopshop.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.ndkien.laptopshop.domain.Cart;
import vn.ndkien.laptopshop.domain.CartDetail;
import vn.ndkien.laptopshop.domain.Order;
import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.service.OrderService;
import vn.ndkien.laptopshop.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ItemController {
    private final ProductService productService;
    private final OrderService orderService;

    public ItemController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
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

        this.productService.handleProductToCart(email, productId, session, 1);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        User currentUser = new User(); // Tạo mới người dùng

        HttpSession session = request.getSession(false); // lấy session hiện tại của người dùng nếu tồn tại
        long id = (long) session.getAttribute("id");// Lấy id của người dùng từ session; ép kiểu về long
        currentUser.setId(id); // Vì mặc định tạo mới người dùng id = null

        Cart cart = this.productService.fetchByUser(currentUser);

        List<CartDetail> cartDetail = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetail(); // Lấy chi tiết
                                                                                                         // sản phẩm
        double totalPrice = 0;
        for (CartDetail cd : cartDetail) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetail", cartDetail);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);

        return "client/cart/show";
    }

    @PostMapping("/data-cart-delete/{id}")
    public String deleteCartId(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false); // lấy session hiện tại của người dùng nếu tồn tại
        long cartDetailId = id;
        this.productService.handleRemoveCartDetail(cartDetailId, session);

        return "redirect:/cart";

    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();// null
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        Cart cart = this.productService.fetchByUser(currentUser);
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetail();
        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }
        model.addAttribute("cartDetail", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetail();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        // Lấy user đang ở phiên đăng nhập
        User currenUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currenUser.setId(id);

        this.productService.handlePlaceOrderPage(currenUser, session, receiverName,
                receiverAddress,
                receiverPhone);

        return "redirect:/thank";
    }

    @GetMapping("/thank")
    public String thankPage() {
        return "client/cart/thank";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        List<Order> orders = this.orderService.fetchOrderPage(currentUser);

        model.addAttribute("order", orders);

        return "client/cart/order-history";
    }

    @PostMapping("/add-product-from-view-detail")
    public String addProductFromView(HttpServletRequest request, @RequestParam("id") long id,
            @RequestParam("quantity") long quantity) {
        // Lấy session đang hoạt động
        HttpSession session = request.getSession(false);

        // Lấy email đang trong session
        String email = (String) session.getAttribute("email");

        // Logic thêm sản phẩm vào giỏ hàng
        this.productService.handleProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }

    @GetMapping("/products")
    public String getProductPage(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                // convert from String to int
                page = Integer.parseInt(pageOptional.get());
            } else {
                // page = 1
            }
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }
        Pageable pageable = PageRequest.of(page - 1, 6);
        Page<Product> prs = this.productService.getAllProduct(pageable);
        List<Product> products = prs.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", prs.getTotalPages());
        return "client/product/show";
    }

}
