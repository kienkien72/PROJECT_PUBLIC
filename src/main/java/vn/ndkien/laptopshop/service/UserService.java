package vn.ndkien.laptopshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.ndkien.laptopshop.domain.Role;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.domain.auth.Register;
import vn.ndkien.laptopshop.repository.OrderRepository;
import vn.ndkien.laptopshop.repository.ProductRepository;
import vn.ndkien.laptopshop.repository.RoleRepository;
import vn.ndkien.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // Constructor
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // 1. Lưu dữ liệu người dùng khi nhập form
    public User handleSaveUser(User user) {
        User kien = this.userRepository.save(user);
        return kien;
    }

    // 2. Lấy danh sách tất cả người dùng
    public Page<User> getAllUser(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    // 3. Lấy danh sách người dùng bởi id
    public User getOneUserId(long id) {
        return this.userRepository.findById(id);
    }

    // 4. Xoá người dùng theo id
    public void deleteUserId(long id) {
        this.userRepository.deleteById(id);
    }

    // 5. Lấy Role
    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    // Lưu người dùng vào database
    public User registerDTOtoUser(Register register) {
        User user = new User();

        user.setFullname(register.getFirstName() + " " + register.getLastName());
        user.setEmail(register.getEmail());
        user.setPassword(register.getPassword());

        return user;
    }

    // Kt email có tồn tại hay chưa
    public boolean checkEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public long countUser() {
        return this.userRepository.count();
    }

    public long countProduct() {
        return this.productRepository.count();
    }

    public long countOrder() {
        return this.orderRepository.count();
    }
}
