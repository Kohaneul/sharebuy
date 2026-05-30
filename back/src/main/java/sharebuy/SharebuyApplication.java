package sharebuy;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static sharebuy.domain.page.provider.pagedata.ContextConstants.KAKAO_MAP_KEY;

@SpringBootApplication
public class SharebuyApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty(KAKAO_MAP_KEY,dotenv.get(KAKAO_MAP_KEY));
        SpringApplication.run(SharebuyApplication.class, args);
    }
}
