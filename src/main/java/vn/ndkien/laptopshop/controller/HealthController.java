package vn.ndkien.laptopshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        // Return a simple plain text response for Railway healthcheck
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain")
                .body("OK");
    }

    @GetMapping("/ping")
    public void ping(HttpServletResponse response) throws IOException {
        // Simple ping endpoint that returns HTTP 200
        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("pong");
    }

    @GetMapping("/health.json")
    public ResponseEntity<Map<String, String>> healthJson() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("application", "Laptop Shop API");
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