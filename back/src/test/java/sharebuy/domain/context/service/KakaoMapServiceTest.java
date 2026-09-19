package sharebuy.domain.context.service;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sharebuy.SharebuyApplication;
import sharebuy.common.domain.Location;
@ActiveProfiles("test")
@Slf4j
@SpringBootTest(
        classes = SharebuyApplication.class
)
class KakaoMapServiceTest {
    @Autowired
    KakaoMapService kakaoMapService;

    /**
     * 주소-> 좌표 서비스
     */
    @Test
    public void convertPrimaryAddressToCoordinate(){
        String primaryAddress="경기도 군포시 금정동 849";
        Location location = kakaoMapService.getCoordinateByAddress(primaryAddress);
        log.info("lat = {},lng = {}",location.getLatitude(),location.getLongitude());
        Assertions.assertThat(location).isNotNull();
    }
    /**
     * 주소-> 좌표 서비스
     */
    @Test
    public void convertCoordinateToPrimaryAddress(){
        Location location = new Location(37.3580653447003,126.93816568048);
        String primaryAddress =  kakaoMapService.getAddressByCoordinate(location);
        log.info("address = {}",primaryAddress);
        Assertions.assertThat(primaryAddress).isNotNull();
    }

}