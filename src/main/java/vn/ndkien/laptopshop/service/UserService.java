package vn.ndkien.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleUser(User user) {
        User ndkien = this.userRepository.save(user);
        System.out.println(ndkien);
        return ndkien;
    }
}
