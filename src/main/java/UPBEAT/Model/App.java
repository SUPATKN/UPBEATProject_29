package UPBEAT.Model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableWebSocketMessageBroker
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
