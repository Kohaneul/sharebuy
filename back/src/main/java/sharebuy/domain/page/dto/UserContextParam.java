package sharebuy.domain.page.dto;

import lombok.extern.slf4j.Slf4j;
import sharebuy.domain.user.entity.User;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static sharebuy.domain.page.provider.pagedata.ContextConstants.LAT;
import static sharebuy.domain.page.provider.pagedata.ContextConstants.LNG;
@Slf4j
public record UserContextParam(Map<String, String> params, User user) {
    public Double getLat() {
        try {
            String value = params.get(LAT);
            return value != null && !value.isBlank() ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }

    }

    public Double getLng() {
        try {
            String value = params.get(LNG);
            return value != null && !value.isBlank() ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public UUID getUUID(String key) {
        String value = params.get(key);
        return value != null ? UUID.fromString(value) : null;
    }

    public <T> T get(String key, Function<String,T> converter){
        String value = params.get(key);
        if(value==null || value.isBlank()) return null;
        try{
            return converter.apply(value);
        }
        catch (IllegalArgumentException e) {
            // 파싱 실패 시 서버가 터지지 않게 로그만 남기고 null 반환
            log.warn("파라미터 변환 실패 - Key: {}, Value: {}, Error: {}", key, value, e.getMessage());
            return null;
        }
    }

}
