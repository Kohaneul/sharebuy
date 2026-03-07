package sharebuy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        // 주소창에 /login이나 /board를 치고 들어오면
//        // 서버가 에러를 내거나 리다이렉트 시키는 대신, Vue 화면(forward:/)을 보여주게 합니다.
//        registry.addViewController("/login").setViewName("forward:/");
//        registry.addViewController("/board").setViewName("forward:/");
//    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*")
                .allowCredentials(true);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
