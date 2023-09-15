package org.screenwork.screenworksv1.testing.cake;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api")
class MyController {


    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
