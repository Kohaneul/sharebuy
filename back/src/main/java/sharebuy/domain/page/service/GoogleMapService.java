package sharebuy.domain.page.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sharebuy.domain.user.domain.Address;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * 현 위치 호출
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleMapService {

    private final RestTemplate restTemplate;

    @Value("${google.api.key}")
    private String apiKey;

    public Address convertAddressFromGoogleApi(Double lat,Double lng){
        String url = format(
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=%f,%f&key=%s&language=ko",
                lat, lng,apiKey);
        try{
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if(response != null && "OK".equals(response.get("status"))){
                List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
                if(results != null && !results.isEmpty()){
                    Map<String, Object> firstResult = results.get(0);
                    String fullAddress = (String) firstResult.get("formatted_address");
                    return parseAddress(fullAddress,lat,lng);
                }
            }
            return null;
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
