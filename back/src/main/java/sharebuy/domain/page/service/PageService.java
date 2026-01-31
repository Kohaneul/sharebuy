package sharebuy.domain.page.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.menu.provider.TopNavProvider;
import sharebuy.domain.menu.service.MenuService;
import sharebuy.domain.page.dto.*;
import sharebuy.domain.page.dto.CardSectionMeta.CardItemMeta;
import sharebuy.domain.page.dto.GridSectionMeta.GridItemMeta;
import sharebuy.domain.page.dto.TopNavMeta.TopNavItemMeta;
import sharebuy.domain.page.entity.Page;
import sharebuy.domain.page.entity.PageSection;
import sharebuy.domain.page.repository.PageRepository;
import sharebuy.domain.post.type.PageSectionType;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.service.UserService;

import java.util.Comparator;
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

        PageMeta pageMeta = getPageMeta(menu.getId(),user.getRoleType());
        TopNavMeta topNavMeta = new TopNavMeta(topNavItemMetas);

        PermissionMeta permissionMeta =permissionMetaAssembler.assemble(user.getRoleType(), menu);

        return new PageContextResponse(topNavMeta, pageMeta,permissionMeta);
    }


    private PageMeta getPageMeta(UUID menuId, RoleType userRoleType) {
        Page page = pageRepository.findByMenuId(menuId);
        List<PageSection> accessiblePageSection = page.getPageSectionList().stream().filter(section -> userRoleType.canAccess(section.getRoleType())).sorted(Comparator.comparing(PageSection::getSortOrder)).toList();

        accessiblePageSection.stream().map(section->{
            switch(section.getPageSectionType()){
                case CARD:
                    return new CardSectionMeta(fetchCardItems(section));
                case GRID:
                    return new GridSectionMeta(fetchGridItems(section));
                case INPUT:
                    return new InputSectionMeta(fetchInputItems(section));
                default:
                    throw new IllegalStateException("Unknown PageType: " + section.getPageSectionType());
            }

        });
        return null;
    }

    private List<InputSectionMeta.InputItem> fetchInputItems(PageSection section) {
        return null;
    }

    private List<GridItemMeta> fetchGridItems(PageSection section) {
        return null;
    }

    private List<CardItemMeta> fetchCardItems(PageSection section) {
        return null;
    }

    ;


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
