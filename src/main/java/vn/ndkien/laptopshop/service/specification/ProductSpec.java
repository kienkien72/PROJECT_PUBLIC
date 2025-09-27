package vn.ndkien.laptopshop.service.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.domain.Product_;

public class ProductSpec {

    // Filter name
    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }

    // Filter min price
    public static Specification<Product> priceLike(double price) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), Double.valueOf(price));

    }

    // Filter max price
    public static Specification<Product> maxPrice(double price) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), Double.valueOf(price));
    }

    // Filter factory
    public static Specification<Product> factoryLike(String factory) {
        return (root, query, cb) -> cb.equal(root.get(Product_.FACTORY), factory);
    }

    // Filter many factory
    public static Specification<Product> listFactoryLike(List<String> factory) {
        return (root, query, cb) -> cb.in(root.get(Product_.FACTORY)).value(factory);
    }

    public static Specification<Product> matchPrice(double min, double max) {
        return (root, query, cb) -> cb.and(
                cb.greaterThanOrEqualTo(root.get(Product_.PRICE), min),
                cb.lessThanOrEqualTo(root.get(Product_.PRICE), max));
    }

}
