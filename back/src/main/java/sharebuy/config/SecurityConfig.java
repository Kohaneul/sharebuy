package sharebuy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CORS 설정: 5173(Vite)에서 오는 요청 허용
                .cors(cors->cors.configurationSource(request->{
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(java.util.List.of("http://localhost:5173")); // Vite 주소 허용
                    config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(java.util.List.of("*"));
                    config.setAllowCredentials(true); // 중요: 세션/쿠키 연동 허용
                    return config;
                }))
                //csrf 비활성화
                .csrf(csrf->csrf.disable())
                // 3. 권한 설정: 화면 주소와 인증 관련 API는 무조건 통과
                .authorizeHttpRequests(auth->auth.requestMatchers("/","/login","/board","/rest/auth/**","/rest/page/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->form
                        .loginProcessingUrl("/rest/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((req, res, auth) -> res.setStatus(200)) // 성공 시 리다이렉트 방지
                        .failureHandler((req, res, exp) -> res.setStatus(401)) // 실패 시 401 반환
                        .permitAll())
                .logout(logout->logout
                        .logoutUrl("/rest/auth/logout")
                        .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
