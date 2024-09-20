package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloContronller {
    @GetMapping("/")
    public String index() {
        return "Nguyễn Duy Kiên sẽ thực tập IT vào tháng 3";
    }

}
