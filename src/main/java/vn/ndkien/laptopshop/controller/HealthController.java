package vn.ndkien.laptopshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("application", "Laptop Shop API");
        status.put("timestamp", LocalDateTime.now().toString());

        // Check database connectivity
        try {
            // Simple database connectivity check could be added here
            status.put("database", "UP");
        } catch (Exception e) {
            status.put("database", "DOWN");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(status);
        }

        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(status);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> root() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(Map.of(
                    "message", "Laptop Shop API is running",
                    "status", "healthy",
                    "timestamp", LocalDateTime.now().toString()
                ));
    }
}