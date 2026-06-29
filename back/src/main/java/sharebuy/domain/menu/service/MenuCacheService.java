package sharebuy.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.dto.MenuResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuCacheService {

    private final RedisTemplate<String, List<MenuResponse>> redisTemplate;

    private final String MENU_KEY_PREFIX = "menu:";

    public List<MenuResponse> get(RoleType roleType){
        return redisTemplate.opsForValue().get(getKey(roleType));
    }

    public void save(RoleType roleType,List<MenuResponse> menuResponses){
        redisTemplate.opsForValue().set(getKey(roleType),menuResponses);
    }

    private String getKey(RoleType roleType){
        return MENU_KEY_PREFIX+roleType.name();
    }



}
