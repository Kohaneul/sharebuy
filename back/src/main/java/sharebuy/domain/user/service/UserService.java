package sharebuy.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.domain.context.service.KakaoMapService;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.dto.ViewerResponse;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final KakaoMapService kakaoMapService;

    public User findById(UUID id){
        return userRepository.findById(id).orElseThrow(()->new IllegalStateException("유저 정보가 존재하지 않습니다."));
    }


    @Transactional(readOnly = true)
    public ViewerResponse getViewerResponse(CustomUserDetail principal, Double latitude, Double longitude) {
        if(principal==null){
            Address guestAddress = kakaoMapService.convertAddressFromKakaoApi(latitude, longitude);
            User guest = User.guest(guestAddress);
            return new ViewerResponse(guest.getLoginId(),guest.getRoleType(),guestAddress.getLatitude(), guestAddress.getLongitude());
        }
        return  new ViewerResponse(principal.getLoginId(), principal.getRoleType(), principal.getLatitude(), principal.getLongitude());
    }
}
