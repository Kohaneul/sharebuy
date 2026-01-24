package sharebuy.domain.menu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.menu.repository.MenuRepository;

import java.util.UUID;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public Menus findById(UUID id){
        return menuRepository.findById(id).orElseThrow(()->new IllegalStateException("정보가 존재하지 않습니다."));
    }

}
