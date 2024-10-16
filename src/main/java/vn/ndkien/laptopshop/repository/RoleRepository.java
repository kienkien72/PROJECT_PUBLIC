package vn.ndkien.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.ndkien.laptopshop.domain.Role;
import vn.ndkien.laptopshop.domain.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
