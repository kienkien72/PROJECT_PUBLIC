package vn.ndkien.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.ndkien.laptopshop.domain.Order;
import vn.ndkien.laptopshop.service.OrderService;

@Controller
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getDashBoard(Model model, Order order) {
        List<Order> currentOrder = this.orderService.getAllOrder(order);
        model.addAttribute("order", currentOrder);
        return "admin/order/table-order";
    }

}
