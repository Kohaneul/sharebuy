package sharebuy.domain.page.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.menu.provider.TopNavProvider;
import sharebuy.domain.menu.service.MenuService;
import sharebuy.domain.page.dto.*;
import sharebuy.domain.page.entity.Page;
import sharebuy.domain.page.repository.PageRepository;
import sharebuy.domain.user.entity.Users;
import sharebuy.domain.user.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
public class PageService {

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;
    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private PermissionMetaAssembler permissionMetaAssembler;


    private final List<TopNavProvider> topNavProviders;

    public PageService(List<TopNavProvider> topNavProviders) {
        this.topNavProviders = topNavProviders;
    }


    public PageContextResponse findById(UUID menuId, UUID userId){
        Menus menu = menuService.findById(menuId);
        Users user = userService.findById(userId);

        List<TopNavMeta.TopNavItemMeta> topNavItemMetas = getTopNavItemMetaList(menu, user);
        PageMeta pageMeta = getPageMeta(menu.getId());
        TopNavMeta topNavMeta = new TopNavMeta(topNavItemMetas);

        PermissionMeta permissionMeta =permissionMetaAssembler.assemble(user, menu);

        return new PageContextResponse(topNavMeta,pageMeta,permissionMeta);
    }


    private PageMeta getPageMeta(UUID menuId) {
        List<Page> page = pageRepository.findByMenuId(menuId);


        return null;
    }


    private List<TopNavMeta.TopNavItemMeta> getTopNavItemMetaList(Menus menu, Users user) {
        List<TopNavMeta.TopNavItemMeta> topNavItemMetas = menu.getTopNavItems()
                .stream()
                .filter(item -> TopNavComponent.isNeedValue(item.getComponent()))
                .map(item -> {
                    boolean needValue = true;
                    TopNavComponent component = item.getComponent();

                    TopNavProvider topNavProvider = topNavProviders.stream()
                            .filter(p -> p.getType() == component).findFirst()
                            .orElseThrow(() -> new IllegalStateException("없는 타입입니다."));

                    Object value = topNavProvider.getValue(user, menu);

                    return new TopNavMeta.TopNavItemMeta(component, needValue, item.getPosition(), value);

                }).toList();
        return topNavItemMetas;
    }

}
