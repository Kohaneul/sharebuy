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
import sharebuy.domain.page.handler.PageSectionMetaHandler;
import sharebuy.domain.page.repository.PageRepository;
import sharebuy.domain.post.type.PageSectionType;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.service.UserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private final Map<PageSectionType, PageSectionMetaHandler> pageSectionMetaHandlerMap;
    private final List<TopNavProvider> topNavProviders;

    public PageService(List<PageSectionMetaHandler> pageSectionMetaHandlers, List<TopNavProvider> topNavProviders) {
        this.pageSectionMetaHandlerMap = pageSectionMetaHandlers.stream().collect(Collectors.toMap(PageSectionMetaHandler::getType,Function.identity()));
        this.topNavProviders = topNavProviders;
    }


    public PageContextResponse getPageContext(UUID menuId, CustomUserDetail principal){
        Menu menu = menuService.findById(menuId);
        User user = (principal!=null) ? principal.getUser() : null;

        //meta 1 -> top_nav 메타정보 가져오기
        TopNavMeta topNavMeta = getTopNavMeta(menu, user);

        //meta 2 -> 페이지 랜더링할 메타정보 가져오기
        PageMeta pageMeta = getPageMeta(menu.getId(),user.getRoleType());

        //meta 3 -> 권한 정보 관련 메타 가져오기
        PermissionMeta permissionMeta =permissionMetaAssembler.assemble(user.getRoleType(), menu);

        //전체 response 객체 셋팅해서 조립
        return new PageContextResponse(topNavMeta, pageMeta, permissionMeta);
    }

    private TopNavMeta getTopNavMeta(Menu menu, User user) {
        List<TopNavItemMeta> topNavItemMetas = getTopNavItemMetaList(menu, user);
        TopNavMeta topNavMeta = new TopNavMeta(topNavItemMetas);
        return topNavMeta;
    }

    /**
     * PageMeta 가져오기
     * @param menuId
     * @param userRoleType
     * @return
     */
    private PageMeta getPageMeta(UUID menuId, RoleType userRoleType) {
        Page page = pageRepository.findByMenuId(menuId);

        List<PageSection> accessiblePageSection = page.getPageSectionList().stream()
                .filter(section -> userRoleType.canAccess(section.getRoleType()))
                .sorted(Comparator.comparing(PageSection::getSortOrder)).toList();

        List<TypeSectionMeta<?>> list = getTypeSectionMetas(accessiblePageSection);

        return new PageMeta(list);
    }

    private List<TypeSectionMeta<?>> getTypeSectionMetas(List<PageSection> accessiblePageSection) {
        List<TypeSectionMeta<?>> list = new ArrayList<>();
        for (PageSection pageSection : accessiblePageSection) {
            PageSectionMetaHandler handler = pageSectionMetaHandlerMap.get(pageSection);
            if(handler==null){
                throw new IllegalStateException("존재하지 않은 페이지 타입입니다.");
            }
            list.add(handler.handle(pageSection));
        }
        return list;
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
