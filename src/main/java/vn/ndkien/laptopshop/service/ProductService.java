package vn.ndkien.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.ndkien.laptopshop.domain.Product;
import vn.ndkien.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 1. Lưu sản phẩm khi nhập form
    public Product handleSaveProduct(Product product) {
        Product kien = this.productRepository.save(product);
        return kien;
    }

    // 2. Lấy tất cả sản phẩm
    public List<Product> getAllProduct(Product product) {
        return this.productRepository.findAll();
    }

}
