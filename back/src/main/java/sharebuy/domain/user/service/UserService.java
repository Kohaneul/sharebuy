package sharebuy.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharebuy.domain.user.entity.Users;
import sharebuy.domain.user.repository.UserRepository;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Users findById(UUID id){
        return userRepository.findById(id).orElseThrow(()->new IllegalStateException("유저 정보가 존재하지 않습니다."));
    }
}
