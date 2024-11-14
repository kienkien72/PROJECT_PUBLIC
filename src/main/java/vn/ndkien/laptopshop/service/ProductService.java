package vn.ndkien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.ndkien.laptopshop.domain.Cart;
import vn.ndkien.laptopshop.domain.CartDetail;
import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.repository.CartDetailRepository;
import vn.ndkien.laptopshop.repository.CartRepository;
import vn.ndkien.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CartDetailRepository cartDetailRepository;
    private CartRepository cartRepository;
    private UserService userService;

    public ProductService(ProductRepository productRepository,
            CartDetailRepository cartDetailRepository,
            CartRepository cartRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    // 1. Lưu sản phẩm khi nhập form
    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    // 2. Lấy tất cả sản phẩm
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    // 3. Lấy theo id sản phẩm
    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    // Xoá Product
    public void deleteId(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleProductToCart(String email, long productId, HttpSession session) {
        // Lấy thông tin người dùng bằng email
        User user = this.userService.getUserByEmail(email);

        // check user đã có Cart chưa ? Nếu chưa => tạo mới
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);

            // Tạo mới cart(giỏ hàng)
            if (cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);

                // Lưu cart sau khi tạo
                cart = this.cartRepository.save(otherCart);
            }

            // Lưu cart_detail
            // Tìm product theo id
            Optional<Product> product = this.productRepository.findById(productId);

            // KT có tồn tại hay không
            if (product.isPresent()) {

                // Trong method Optional dùng phương thức .get() để lấy dữ liệu
                Product realProduct = product.get();

                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);
                // Tạo mới cartDetail
                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setQuantity(1);
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    this.cartDetailRepository.save(cd);

                    // update_card
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);

                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }

    }

}
