package vn.ndkien.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.ndkien.laptopshop.domain.Order;
import vn.ndkien.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Lấy tất cả sản phẩm
    public List<Order> getAllOrder(Order order) {
        return this.orderRepository.findAll();
    }

}
