package pl.mswierczynski.pp5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mswierczynski.pp5.productcatalog.DBConnector;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        DBConnector.connect();
    }
}
