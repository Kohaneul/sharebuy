package sharebuy.domain.context.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.domain.page.dto.ContextParam;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.service.UserService;

import java.util.Map;

@Service
public class UserContextService {
    private final GoogleMapService googleMapService; // 구글 API 호출용 서비스
    private final UserService userService;

    public UserContextService(GoogleMapService googleMapService, UserService userService) {
        this.googleMapService = googleMapService;
        this.userService = userService;
    }


    /**
     * User 정보 셋팅(로그인/비로그인 시 )
     * @param principal
     * @param session
     * @param lat
     * @param lng
     * @return
     */
    public User getUser(CustomUserDetail principal, HttpSession session, Double lat, Double lng) {
        //CASE 1 ) 로그인 시 user 정보 가져옴
        if(principal !=null){
            return userService.findById(principal.getId());
        }
        //CASE 2) 로그인 x
        //위도, 경도 정보가 없으면 현위치 기반으로 뽑아온다.
        String GUEST_ADDRESS = "GUEST_ADDRESS";
        if(lat != null && lng !=null){
            Address guestAddress = googleMapService.convertAddressFromGoogleApi(lat, lng);
            session.setAttribute(GUEST_ADDRESS,guestAddress);
            return User.guest(guestAddress);
        }
        //GUEST_ADDRESS 정보가 세션에 저장되어있으면 해당 값을 가져옴
        Address cachedAddress = (Address) session.getAttribute(GUEST_ADDRESS);
        if (cachedAddress == null) {
            return User.guest(Address.getDefaultAddress());
        }
        return User.guest(cachedAddress);
    }
}
