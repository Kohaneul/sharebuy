package sharebuy.domain.post.controller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {

        public static void main(String[] args) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encoded = encoder.encode("admin");
            System.out.println(encoded);
        }
}
