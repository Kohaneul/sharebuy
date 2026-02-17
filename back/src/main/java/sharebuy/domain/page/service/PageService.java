package sharebuy.domain.page.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.menu.entity.TopNavItem;
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

import static sharebuy.common.domain.RoleType.GUEST;

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

    @Transactional(readOnly = true)
    public PageContextResponse getPageContext(UUID menuId, CustomUserDetail principal){
        Menu menu = menuService.findById(menuId);
        User user = (principal!=null) ? principal.getUser() : User.guest();
        RoleType roleType = user.getRoleType();

        //해당 메뉴가 접근가능한지 확인
        validationAccessMenu(roleType,menu.getRoleType());

        //meta 1 -> top_nav 메타정보 가져오기
        TopNavMeta topNavMeta = getTopNavMeta(menu, user);

        //meta 2 -> 페이지 랜더링할 메타정보 가져오기
        PageMeta pageMeta = getPageMeta(menu.getId(),roleType);

        //meta 3 -> 권한 정보 관련 메타 가져오기
        PermissionMeta permissionMeta =permissionMetaAssembler.assemble(roleType, menu);

        //전체 response 객체 셋팅해서 조립
        return new PageContextResponse(topNavMeta, pageMeta, permissionMeta);
    }

    /**
     * 메뉴가 현 유저가 접근 가능한 메뉴인지 확인
     * @param userRoleType
     * @param menuRoleType
     */
    private void validationAccessMenu(RoleType userRoleType, RoleType menuRoleType) {
        if(!userRoleType.canAccess(menuRoleType)){
            throw new IllegalStateException("["+menuRoleType+"]만 접근가능한 메뉴입니다. 현재 나의 권한:["+userRoleType+"]");
        }
    }

    private TopNavMeta getTopNavMeta(Menu menu, User user) {
        List<TopNavItemMeta> topNavItemMetas = getTopNavItemMetaList(menu, user);
        return new TopNavMeta(topNavItemMetas);
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
            PageSectionMetaHandler handler = pageSectionMetaHandlerMap.get(pageSection.getPageSectionType());
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

    /**
     * top_nav 메타데이터 조립
     * @param menu
     * @param user
     * @return
     */
    private List<TopNavItemMeta> getTopNavItemMetaList(Menu menu, User user) {
        return menu.getTopNavItems()
                .stream()
                .filter(item -> user.getRoleType().canAccess(item.getRoleType()))
                .sorted(Comparator.comparing(TopNavItem::getPosition).thenComparing(TopNavItem::getDisplayOrder))
                .map(item -> {
                    boolean needValue = TopNavComponent.isNeedValue(item.getComponent());

                    TopNavComponent component = item.getComponent();

                    Object value = null;
                    if(needValue){
                        TopNavProvider topNavProvider = topNavProviders.stream()
                                .filter(p -> p.getType() == component).findFirst()
                                .orElseThrow(() -> new IllegalStateException("없는 타입입니다."));

                        value = topNavProvider.getValue(user, menu);
                    }

                    return new TopNavItemMeta(component, needValue, item.getPosition(), value);
                }).toList();
    }

}
