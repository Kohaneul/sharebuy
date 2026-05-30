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
import sharebuy.domain.user.domain.Address;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * 현 위치 호출
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoMapService {

    private final RestTemplate restTemplate;
    private static final String OK = "OK";
    private static final String KAKAO_COORD_TO_ADDRESS_URL =
            "https://dapi.kakao.com/v2/local/geo/coord2address.json";
    @Value("${kakao.map.key}")
    private String apiKey;

    public Address convertAddressFromKakaoApi(Double lat,Double lng){
        String url = UriComponentsBuilder.fromHttpUrl(KAKAO_COORD_TO_ADDRESS_URL)
                    .queryParam("x", lng)
                    .queryParam("y", lat)
                    .toUriString();
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + apiKey);

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map body = response.getBody();
            List<Map<String, Object>> documents = (List<Map<String, Object>>) body.get("documents");
            if(documents.isEmpty()){
                return null;
            }
            Map<String, Object> map = documents.get(0);
            Map<String, Object> address = (Map<String, Object>) map.get("address");
            String primaryAddress = (String) address.get("address_name");
            return new Address(primaryAddress,null,null,lat,lng);
        }
        catch(RuntimeException e){
            log.error("실패 -> **찾을 수 없는 좌표입니다.");
        }


       return null;
    }

    private Address parseAddress(String fullAddress, Double lat, Double lng) {
        String guestZipCode = "0000";
        String[] parts = fullAddress.split(" ");
        StringBuilder primaryAddress = new StringBuilder();
        StringBuilder detailAddress = new StringBuilder();
        boolean foundSplitPoint = false;
        for (String part : parts) {
            if(!foundSplitPoint){
                if(part.equals("대한민국")){
                    continue;
                }
                primaryAddress.append(part).append(" ");
                if(part.endsWith("동")||part.endsWith("읍")||part.endsWith("면")){
                    foundSplitPoint = true;
                }
            }
            else{
                detailAddress.append(part).append(" ");
            }
        }
        return new Address(primaryAddress.toString(),detailAddress.toString(),guestZipCode,lat,lng);
    }

}
