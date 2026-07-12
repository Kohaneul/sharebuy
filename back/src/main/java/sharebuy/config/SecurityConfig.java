package sharebuy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import sharebuy.common.auth.config.CustomUserDetail;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;

    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. CORS 설정: 5173(Vite)에서 오는 요청 허용
                .cors(cors->cors.configurationSource(request->{
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(java.util.List.of("http://localhost:5173","http://localhost:5174")); // Vite 주소 허용
                    config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(java.util.List.of("*"));
                    config.setAllowCredentials(true); // 중요: 세션/쿠키 연동 허용
                    return config;
                }))
                //csrf 비활성화
                .csrf(csrf->csrf.disable())
                // 3. 권한 설정: 화면 주소와 인증 관련 API는 무조건 통과
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/","/login","/board","/rest/auth/**").permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/rest/user/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/rest/page/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/rest/post/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/rest/menu/all", "GET")).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->form
                        .loginProcessingUrl("/rest/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((req,res,auth)->{
                            //로그인 성공시 반환값 설정
                            CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
                            //응답데이터 맵 구성
                            Map<String,Object> data = new HashMap<>();
                            data.put("latitude", userDetail.getLatitude());
                            data.put("longitude", userDetail.getLongitude());
                            data.put("roleType", userDetail.getRoleType().name());
                            data.put("loginId", userDetail.getLoginId());
                            res.setContentType("application/json;charset=UTF-8");
                            res.setStatus(200);

                            res.getWriter().write(objectMapper.writeValueAsString(data));
                        }) // 성공 시 리다이렉트 방지
                        .failureHandler((req, res, exp) -> res.setStatus(401)) // 실패 시 401 반환
                        .permitAll())
                .logout(logout->logout
                        .logoutUrl("/rest/auth/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
