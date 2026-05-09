package sharebuy.domain.context.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.domain.page.dto.UserContextParam;
import sharebuy.domain.page.provider.pagedata.ContextConstants;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.service.UserService;

import java.util.Map;

import static sharebuy.domain.page.provider.pagedata.ContextConstants.*;

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
     * @param paramMap
     * @return
     */
    public UserContextParam getUserContextParam(CustomUserDetail principal, HttpSession session, Map<String,String> paramMap) {
        UserContextParam userContextParam = new UserContextParam(paramMap,null);
        //CASE 1 ) 로그인 시 user 정보 가져옴
        if(principal !=null){
            User user = userService.findById(principal.getId());
            putParamMap(paramMap, user);
            return new UserContextParam(paramMap,user);
        }

        //CASE 2) 로그인 x
        //위도, 경도 정보가 없으면 현위치 기반으로 뽑아온다.
        if(!paramMap.isEmpty()){
            Address guestAddress = googleMapService.convertAddressFromGoogleApi(userContextParam.getLat(), userContextParam.getLng());
            session.setAttribute(GUEST_ADDRESS,guestAddress);
            User guest = User.guest(guestAddress);

            return new UserContextParam(paramMap,guest);
        }
        //GUEST_ADDRESS 정보가 세션에 저장되어있으면 해당 값을 가져옴
        Address cachedAddress = (Address) session.getAttribute(GUEST_ADDRESS);

        if (cachedAddress == null) {
            User guest = User.guest(Address.getDefaultAddress());
            putParamMap(paramMap, guest);
            return new UserContextParam(paramMap,guest);
        }
        return new UserContextParam(paramMap,User.guest(cachedAddress));
    }

    private void putParamMap(Map<String, String> paramMap, User user) {
        Double lng;
        Double lat;
        if(paramMap.isEmpty()){
            Address address = user.getAddress();
            lat = address.getLatitude();
            lng = address.getLongitude();
            paramMap.put(LAT,String.valueOf(lat));
            paramMap.put(LNG,String.valueOf(lng));
        }
    }
}
