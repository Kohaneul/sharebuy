package sharebuy.domain.page.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.menu.provider.TopNavProvider;
import sharebuy.domain.menu.service.MenuService;
import sharebuy.domain.page.dto.*;
import sharebuy.domain.page.dto.TopNavMeta.TopNavItemMeta;
import sharebuy.domain.page.entity.Page;
import sharebuy.domain.page.repository.PageRepository;
import sharebuy.domain.user.entity.User;
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


    public PageContextResponse getPageContext(UUID menuId, CustomUserDetail principal){
        Menu menu = menuService.findById(menuId);
        User user = principal.getUser();

        List<TopNavItemMeta> topNavItemMetas = getTopNavItemMetaList(menu, user);

        PageMeta pageMeta = getPageMeta(menu.getId());
        TopNavMeta topNavMeta = new TopNavMeta(topNavItemMetas);

        PermissionMeta permissionMeta =permissionMetaAssembler.assemble(user.getRoleType(), menu);

        return new PageContextResponse(topNavMeta, pageMeta,permissionMeta);
    }


    private PageMeta getPageMeta(UUID menuId) {
        List<Page> page = pageRepository.findByMenuId(menuId);


        return null ;
    }


    private List<TopNavItemMeta> getTopNavItemMetaList(Menu menu, User user) {
        return menu.getTopNavItems()
                .stream()
                .filter(item -> TopNavComponent.isNeedValue(item.getComponent()))
                .map(item -> {
                    boolean needValue = true;
                    TopNavComponent component = item.getComponent();

                TopNavProvider topNavProvider = topNavProviders.stream()
                            .filter(p -> p.getType() == component).findFirst()
                            .orElseThrow(() -> new IllegalStateException("없는 타입입니다."));

                    Object value = topNavProvider.getValue(user, menu);

                    return new TopNavItemMeta(component, needValue, item.getPosition(), value);

                }).toList();
    }

}
