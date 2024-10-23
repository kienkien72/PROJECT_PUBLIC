package vn.ndkien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.User;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Trả về một người dùng theo id
    Product findById(long id);
}
