package sharebuy.domain.page.service;

import jakarta.servlet.http.HttpSession;
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
import sharebuy.domain.page.dto.TopNavMeta.TopNavItemMeta;
import sharebuy.domain.page.entity.Page;
import sharebuy.domain.page.entity.PageSection;
import sharebuy.domain.page.repository.PageRepository;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.service.UserService;

import java.util.*;

@Service
public class PageService {

    private final MenuService menuService;
    private final UserService userService;
    private final PageRepository pageRepository;
    private final GoogleMapService googleMapService; // 구글 API 호출용 서비스

    @Autowired
    private PermissionMetaAssembler permissionMetaAssembler;

    private final List<TopNavProvider> topNavProviders;

    public PageService(MenuService menuService, UserService userService, PageRepository pageRepository, GoogleMapService googleMapService, List<TopNavProvider> topNavProviders) {
        this.menuService = menuService;
        this.userService = userService;
        this.pageRepository = pageRepository;
        this.googleMapService = googleMapService;
        this.topNavProviders = topNavProviders;
    }

    @Transactional(readOnly = true)
    public PageContextResponse getPageContext(UUID menuId, CustomUserDetail principal, HttpSession session,Double lat,Double lng){
        Menu menu = menuService.findById(menuId);

        //user 정보 추출
        User user = getUser(principal, session,lat,lng);

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

    private User getUser(CustomUserDetail principal, HttpSession session,Double lat,Double lng) {
        if(principal !=null){
            return principal.getUser();
        }
        if(lat != null && lng !=null){
            Address guestAddress = googleMapService.convertAddressFromGoogleApi(lat, lng);
            session.setAttribute("GUEST_ADDRESS",guestAddress);
            return User.guest(guestAddress);
        }
        Address cachedAddress = (Address) session.getAttribute("GUEST_ADDRESS");
        if (cachedAddress == null) {
            return User.guest(Address.getDefaultAddress());
        }
        return User.guest(cachedAddress);
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

        List<PageSectionMeta> list = getTypeSectionMetas(accessiblePageSection);

        return new PageMeta(list);
    }


    private List<PageSectionMeta> getTypeSectionMetas(List<PageSection> accessiblePageSection) {
        return accessiblePageSection.stream().map(
                pageSection-> new PageSectionMeta(pageSection.getPageSectionType(), pageSection.getDataUrl())).toList();
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
