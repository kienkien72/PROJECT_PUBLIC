package vn.ndkien.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.ndkien.laptopshop.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User save(User ndkien);

    void deleteById(long id);

    // Định nghĩa về các câu query
    // Trả về list danh sách người dùng
    // List<User> findByEmail(String email);

    // Trả về một người dùng theo id
    User findById(long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
