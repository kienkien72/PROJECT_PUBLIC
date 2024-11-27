package vn.ndkien.laptopshop.service;

import org.springframework.data.jpa.domain.Specification;

import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.Product_;

public class ProductSpec {

    // Filter name
    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }

    // Filter price
    public static Specification<Product> priceLike(double price) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), Double.valueOf(price));

    }

}
