package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloContronller {
    @GetMapping("/")
    public String index() {
        return "xin chào";
    }

    @GetMapping("/user")
    public String UserPage() {
        return "Only user";
    }

    @GetMapping("/admin")
    public String Adminpage() {
        return "Only admin";
    }

}
