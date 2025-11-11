package vn.ndkien.laptopshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.ndkien.laptopshop.domain.Role;
import vn.ndkien.laptopshop.domain.User;
import vn.ndkien.laptopshop.repository.RoleRepository;
import vn.ndkien.laptopshop.service.UserService;

@Controller
public class InitController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/init-admin")
    @ResponseBody
    public String initAdmin() {
        try {
            // Check if admin already exists
            if (userService.checkEmailExists("admin@admin.com")) {
                User existingAdmin = userService.getUserByEmail("admin@admin.com");
                return "Admin user already exists with email: " + existingAdmin.getEmail() +
                       "<br>Role: " + (existingAdmin.getRole() != null ? existingAdmin.getRole().getName() : "NO ROLE");
            }

            // Get ADMIN role
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                // Create ADMIN role if not exists
                adminRole = new Role();
                adminRole.setName("ADMIN");
                adminRole = roleRepository.save(adminRole);
            }

            // Create admin user
            User admin = new User();
            admin.setEmail("admin@admin.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setFullname("Administrator");
            admin.setRole(adminRole);

            User savedAdmin = userService.handleSaveUser(admin);

            return String.format("""
                Admin user created successfully!<br><br>
                Email: admin@admin.com<br>
                Password: 123456<br>
                Role: %s<br>
                User ID: %d<br><br>
                <a href="/login">Click here to login</a>
                """,
                savedAdmin.getRole().getName(),
                savedAdmin.getId());

        } catch (Exception e) {
            return "Error creating admin user: " + e.getMessage() +
                   "<br><br>Stack trace: " + e.toString().replace("\n", "<br>");
        }
    }
}