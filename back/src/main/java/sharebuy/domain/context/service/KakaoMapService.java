package sharebuy.domain.context.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sharebuy.common.domain.Location;
import sharebuy.domain.user.domain.Address;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static sharebuy.domain.page.provider.pagedata.ContextConstants.*;

/**
 * 현 위치 호출
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoMapService {

    private final RestTemplate restTemplate;
    private static final String KAKAO_COORD_TO_ADDRESS_URL = "https://dapi.kakao.com/v2/local/geo/coord2address.json";
    private static final String KAKAO_ADDRESS_TO_COORD_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    @Value("${kakao.map.key}")
    private String apiKey;

    /**
     * 좌표->주소 변환
     * @param location
     * @return
     */
    public String getAddressByCoordinate(Location location){
        URI uri = UriComponentsBuilder
                .fromHttpUrl(KAKAO_COORD_TO_ADDRESS_URL)
                .queryParam("x", location.getLongitude())
                .queryParam("y", location.getLatitude())
                .build()
                .encode()
                .toUri();
        try{
            Map body = requestKakaoApi(uri);
            if(body ==null){
                return null;
            }

            List<Map<String, Object>> documents = (List<Map<String, Object>>) body.get(DOCUMENTS);
            if(documents.isEmpty()){
                return null;
            }
            Map<String, Object> map = documents.get(0);
            Map<String, Object> address = (Map<String, Object>) map.get(ADDRESS);
            return (String) address.get(ADDRESS_NAME);
        }
        catch(RuntimeException e){
            log.error("실패 -> **찾을 수 없는 좌표입니다.");
        }
       return null;
    }

    /**
     * 주소 -> 좌표
     * @param primaryAddress
     * @return
     */
    public Location getCoordinateByAddress(String primaryAddress){
        URI uri = UriComponentsBuilder
                .fromHttpUrl(KAKAO_ADDRESS_TO_COORD_URL)
                .queryParam("query", primaryAddress)
                .build()
                .encode()
                .toUri();
        try{
            Map body = requestKakaoApi(uri);

            if(body ==null){
                return null;
            }
            List<Map<String, Object>> documents = (List<Map<String, Object>>) body.get(DOCUMENTS);
            if(documents ==null || documents.isEmpty()){
                return null;
            }
            Map<String, Object> document = documents.get(0);

            Double longitude = Double.parseDouble(
                    document.get("x").toString()
            );

            Double latitude = Double.parseDouble(
                    document.get("y").toString()
            );

            return new Location(latitude,longitude);
        }
        catch(RuntimeException e){
            log.error("주소 → 좌표 변환 실패. address={}", primaryAddress, e);
        }
        return null;
    }


    private Map requestKakaoApi(URI uri) {
        HttpHeaders headers = new HttpHeaders();
        String api = apiKey.trim();
        headers.set(AUTHORIZATION, KAKAO_AUTH_PREFIX + api);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }

}
