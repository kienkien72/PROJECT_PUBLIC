package vn.ndkien.laptopshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DebugController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/debug/encode")
    public ResponseEntity<Map<String, String>> encodePassword(@RequestParam String password) {
        Map<String, String> response = new HashMap<>();

        String encoded = passwordEncoder.encode(password);
        boolean matches = passwordEncoder.matches(password, encoded);

        response.put("plain", password);
        response.put("encoded", encoded);
        response.put("matches", String.valueOf(matches));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/debug/check-hash")
    public ResponseEntity<Map<String, String>> checkHash() {
        Map<String, String> response = new HashMap<>();

        String plain = "123456";
        String hash = "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi";

        boolean matches = passwordEncoder.matches(plain, hash);

        response.put("plain", plain);
        response.put("hash", hash);
        response.put("matches", String.valueOf(matches));
        response.put("message", matches ? "Password matches the hash!" : "Password does NOT match!");

        return ResponseEntity.ok(response);
    }
}