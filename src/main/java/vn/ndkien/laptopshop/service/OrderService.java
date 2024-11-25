package vn.ndkien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.ndkien.laptopshop.domain.Order;
import vn.ndkien.laptopshop.domain.OrderDetail;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.repository.OrderDetailRepository;
import vn.ndkien.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    // Lấy tất cả đơn hàng
    public Page<Order> getAllOrder(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }

    // Xem chi tiết một đơn hàng
    public Optional<Order> getOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public void updateOrder(Order order) {
        Optional<Order> orderOptional = this.getOrderById(order.getId());
        if (orderOptional.isPresent()) {
            Order currentOrder = orderOptional.get();
            currentOrder.setStatus(order.getStatus());
            this.orderRepository.save(currentOrder);
        }
    }

    public void deleteOrder(long id) {
        // Xoá OrderDetail trước rồi mới xoá Order
        Optional<Order> orderOptional = this.getOrderById(id);
        if (orderOptional != null) {
            Order order = orderOptional.get();
            List<OrderDetail> orderDetails = order.getOrderDetail();
            for (OrderDetail cd : orderDetails) {
                this.orderDetailRepository.deleteById(cd.getId());
            }
            this.orderRepository.deleteById(id);
        }
    }

    public List<Order> fetchOrderPage(User user) {
        return this.orderRepository.findByUser(user);
    }

}
