package sharebuy.domain.menu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.dto.MenuChildResponse;
import sharebuy.domain.menu.dto.MenuResponse;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.menu.repository.MenuRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuCacheService menuCacheService;
    private final MenuRepository menuRepository;


    public Menu findMenuByUser(RoleType roleType, UUID id){
        return menuRepository.findMenuByUser(roleType,id).orElseThrow(()->new IllegalStateException("정보가 존재하지 않습니다."));
    }

    public Menu findById(UUID menuId) {
        return menuRepository.findById(menuId).orElseThrow(()->new RuntimeException("존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> findAll(CustomUserDetail principal) {
        RoleType roleType = principal.getRoleType();
        List<MenuResponse> menuResponseList = menuCacheService.get(roleType);
        if(menuResponseList == null || menuResponseList.isEmpty()){
            List<Menu> menus = menuRepository.findActiveMenusIsActive();
            menuResponseList = convertMenuResponseList(menus,roleType);
            menuCacheService.save(roleType,menuResponseList);
        };
        return menuResponseList;
    }

    /**
     * menu ->menuResponse 객체로 변환
     * @param menus
     * @return
     */
    private List<MenuResponse> convertMenuResponseList(List<Menu> menus,RoleType roleType) {
        List<Menu> accessibleMenus = menus.stream().filter(menu ->roleType.canAccess(menu.getRoleType())).toList();
        Map<UUID, List<Menu>> accessibleChildMap = accessibleMenus.stream().filter(menu -> menu.getParentId() != null).collect(Collectors.groupingBy(Menu::getParentId));

        return accessibleMenus.stream()
                .filter(menu->menu.getParentId()==null)
                .map(parent -> new MenuResponse(
                        parent.getId(),
                        parent.getTitle(),
                        parent.getIcon(),
                        accessibleChildMap.getOrDefault(parent.getId(), List.of())
                                .stream()
                                .map(child -> new MenuChildResponse(
                                        child.getId(),
                                        child.getTitle(),
                                        child.getRouteName()
                                ))
                                .toList()
                ))
                .toList();
           }
}
