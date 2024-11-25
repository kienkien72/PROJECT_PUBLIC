package vn.ndkien.laptopshop.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.ndkien.laptopshop.domain.Cart;
import vn.ndkien.laptopshop.domain.CartDetail;
import vn.ndkien.laptopshop.domain.Order;
import vn.ndkien.laptopshop.domain.OrderDetail;
import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.repository.CartDetailRepository;
import vn.ndkien.laptopshop.repository.CartRepository;
import vn.ndkien.laptopshop.repository.OrderDetailRepository;
import vn.ndkien.laptopshop.repository.OrderRepository;
import vn.ndkien.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private CartDetailRepository cartDetailRepository;
    private CartRepository cartRepository;
    private UserService userService;
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository,
            CartDetailRepository cartDetailRepository,
            CartRepository cartRepository, UserService userService,
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    // 1. Lưu sản phẩm khi nhập form
    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    // 2. Lấy tất cả sản phẩm
    public Page<Product> getAllProduct(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }

    // Lấy tất cả sản phẩm không phân trang
    public List<Product> fetchProductViewPage() {
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

    // Xử lí thêm sản phẩm vào giỏ hàng
    public void handleProductToCart(String email, long productId, HttpSession session, long quantity) {
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
                    cd.setQuantity(quantity);
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    this.cartDetailRepository.save(cd);

                    // update_card
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    session.setAttribute("sum", s); // Cập nhật số lượng giỏ hàng
                    this.cartRepository.save(cart); // Lưu vào giỏ hàng

                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }

    }

    // Tìm giỏ hàng theo người dùng
    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    // Xoá sản phẩm trong giỏ hàng
    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOption = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOption.isPresent()) {
            CartDetail cartDetail = cartDetailOption.get();
            Cart cart = cartDetail.getCart();

            // Xoá cartDetail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (cart.getSum() > 1) {
                int s = cart.getSum() - 1;
                cart.setSum(s);
                session.setAttribute("sum", s); // Cập nhật session mới
                this.cartRepository.save(cart); // Lưu giỏ hảng
            } else {
                // Xoá card
                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);

            }
        }

    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrderPage(User user, HttpSession session,
            String receiverName, String receiverAddress, String receiverPhone) {

        // Lấy thông tin cart theo người dùng
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            // Lấy thông tin cartDetail từ cart
            List<CartDetail> cartDetails = cart.getCartDetail();

            if (cartDetails != null) {
                // Tạo mới Order, gắn các giá trị vào
                Order currentOrder = new Order(0, 0);
                currentOrder.setUser(user);// Lấy user_id theo user
                currentOrder.setReceiverAddress(receiverAddress);
                currentOrder.setReceiverName(receiverName);
                currentOrder.setReceiverPhone(receiverPhone);
                currentOrder.setStatus("status");
                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += cd.getPrice();
                }
                currentOrder.setTotalPrice(sum);

                currentOrder = this.orderRepository.save(currentOrder);

                // Lặp để lấy các thông số gán vào OrderDetail
                for (CartDetail cd : cartDetails) {
                    // Tạo mới orderDetail
                    OrderDetail currenOrderDetail = new OrderDetail(0, 0, 0, currentOrder, null);
                    currenOrderDetail.setOrder(currentOrder); // Lấy order_id
                    currenOrderDetail.setProduct(cd.getProduct()); // Lấy product_id
                    currenOrderDetail.setId(cd.getId());
                    currenOrderDetail.setPrice(cd.getPrice());
                    currenOrderDetail.setQuantity(cd.getQuantity());
                    this.orderDetailRepository.save(currenOrderDetail);
                }

                // Xoá CartDetail
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }
                // Xoá Cart
                this.cartRepository.deleteById(cart.getId());

                // Cập nhật session mới
                session.setAttribute("sum", 0);
            }
        }

    }

}
