package vn.ndkien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.ndkien.laptopshop.domain.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
