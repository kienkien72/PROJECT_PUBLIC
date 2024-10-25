package vn.ndkien.laptopshop.service;

import java.util.List;
import java.util.Optional;

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
        return this.productRepository.save(product);
    }

    // 2. Lấy tất cả sản phẩm
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    // 3. Lấy theo id sản phẩm
    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteId(long id) {
        this.productRepository.deleteById(id);
    }
}
