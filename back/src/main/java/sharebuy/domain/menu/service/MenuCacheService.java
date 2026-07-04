package sharebuy.domain.menu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.SerializationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.dto.MenuResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Qualifier("menuRedisTemplate")
public class MenuCacheService {

    private final RedisTemplate<String, List<MenuResponse>> redisTemplate;

    private final String MENU_KEY_PREFIX = "MENU:";

    public List<MenuResponse> get(RoleType roleType){
        try{
            return redisTemplate.opsForValue().get(getKey(roleType));
        }
        catch (RedisConnectionFailureException e) {
            log.warn("Redis connection failed", e);
            return null;
        } catch (SerializationException e) {
            log.warn("Redis serialization corrupted data", e);
            return null;
        }
    }

    public void save(RoleType roleType,List<MenuResponse> menuResponses){
        redisTemplate.opsForValue().set(getKey(roleType),menuResponses);
    }

    private String getKey(RoleType roleType){
        return MENU_KEY_PREFIX+roleType.name();
    }



}
