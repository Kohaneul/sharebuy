package sharebuy;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SharebuyApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("GOOGLE_MAP_KEY",dotenv.get("GOOGLE_MAP_KEY"));
        SpringApplication.run(SharebuyApplication.class, args);
    }
}
