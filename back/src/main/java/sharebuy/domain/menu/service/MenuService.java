package sharebuy.domain.menu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.menu.repository.MenuRepository;

import java.util.UUID;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menu findMenuByUser(RoleType roleType, UUID id){
        return menuRepository.findMenuByUser(roleType,id).orElseThrow(()->new IllegalStateException("정보가 존재하지 않습니다."));
    }

    public Menu findById(UUID menuId) {
        return menuRepository.findById(menuId).orElseThrow(()->new RuntimeException("존재하지 않습니다."));
    }
}
