package vn.ndkien.laptopshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/test/auth")
    public ResponseEntity<Map<String, Object>> testAuth() {
        Map<String, Object> response = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            response.put("authenticated", true);
            response.put("username", auth.getName());
            response.put("authorities", auth.getAuthorities());
        } else {
            response.put("authenticated", false);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/users")
    public ResponseEntity<Map<String, Object>> testUsers() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Test if we can load the user
            var userDetails = userDetailsService.loadUserByUsername("admin@admin.com");
            response.put("userFound", true);
            response.put("username", userDetails.getUsername());
            response.put("passwordEncoded", userDetails.getPassword());
            response.put("authorities", userDetails.getAuthorities());
        } catch (Exception e) {
            response.put("userFound", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test/password")
    public ResponseEntity<Map<String, Object>> testPassword() {
        Map<String, Object> response = new HashMap<>();

        String plainPassword = "123456";
        String encodedPassword = "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi";

        boolean matches = passwordEncoder.matches(plainPassword, encodedPassword);

        response.put("plainPassword", plainPassword);
        response.put("encodedPassword", encodedPassword);
        response.put("passwordMatches", matches);

        return ResponseEntity.ok(response);
    }
}