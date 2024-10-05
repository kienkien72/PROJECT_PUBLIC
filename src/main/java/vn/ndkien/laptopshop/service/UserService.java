package vn.ndkien.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    // Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. Lưu dữ liệu người dùng khi nhập form
    public User handleSaveUser(User user) {
        User ndkien = this.userRepository.save(user);
        System.out.println(ndkien);
        return ndkien;
    }

    // 2. Lấy danh sách tất cả người dùng
    public List<User> getAllUser(User user) {
        return this.userRepository.findAll();
    }

    // 3. Lấy danh sách tất cả người dùng bởi id
    public User getOneUserId(long id) {
        return this.userRepository.findById(id);
    }

    // 4. Xoá người dùng theo id
    public void deleteUserId(long id) {
        this.userRepository.deleteById(id);
    }
}
