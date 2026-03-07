package sharebuy.common.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService {

    private final UserRepository userRepository;

    public UserDetails loginUser(String loginId){
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetail(user);
    }
}
