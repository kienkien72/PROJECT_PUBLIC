package vn.ndkien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.ndkien.laptopshop.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
