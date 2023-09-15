package org.screenwork.screenworksv1.testing.cake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class WebTesting {

    public static void main(String[] args) {
        SpringApplication.run(WebTesting.class, args);
    }
}

@RestController
@RequestMapping("/api") // Base path for your RESTful API
class MyController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
