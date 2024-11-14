package vn.ndkien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.ndkien.laptopshop.domain.Cart;
import vn.ndkien.laptopshop.domain.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
