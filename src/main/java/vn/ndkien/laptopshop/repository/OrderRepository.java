package vn.ndkien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.ndkien.laptopshop.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
