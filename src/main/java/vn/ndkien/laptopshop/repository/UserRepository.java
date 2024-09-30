package vn.ndkien.laptopshop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import vn.ndkien.laptopshop.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User save(User ndkien);
}
