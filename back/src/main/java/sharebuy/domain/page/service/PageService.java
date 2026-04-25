package sharebuy.domain.page.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.page.domain.TopNavComponent;
import sharebuy.domain.page.dto.*;
import sharebuy.domain.page.dto.TopNavMeta.TopNavItemMeta;
import sharebuy.domain.page.entity.Page;
import sharebuy.domain.page.entity.PageSection;
import sharebuy.domain.page.entity.TopNavItem;
import sharebuy.domain.page.provider.topnav.TopNavProvider;
import sharebuy.domain.page.repository.PageRepository;
import sharebuy.domain.page.repository.TopNavItemRepository;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.service.UserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PageService {

    private final UserService userService;
    private final PageRepository pageRepository;
    private final TopNavItemRepository topNavItemRepository;
    private final GoogleMapService googleMapService; // 구글 API 호출용 서비스
    private final PermissionMetaAssembler permissionMetaAssembler;
    private final Map<TopNavComponent, TopNavProvider> topNavProviderMap;
    private final List<TopNavProvider> topNavProviders;

    public PageService( UserService userService, PageRepository pageRepository, TopNavItemRepository topNavItemRepository, GoogleMapService googleMapService, PermissionMetaAssembler permissionMetaAssembler, List<TopNavProvider> topNavProviders) {
        this.userService = userService;
        this.pageRepository = pageRepository;
        this.topNavItemRepository = topNavItemRepository;
        this.googleMapService = googleMapService;
        this.permissionMetaAssembler = permissionMetaAssembler;
        this.topNavProviderMap = topNavProviders.stream().collect(Collectors.toMap(TopNavProvider::getType, Function.identity()));
        this.topNavProviders = topNavProviders;
    }


    @Transactional(readOnly = true)
    public PageContextResponse getPageContext(UUID pageId, CustomUserDetail principal, HttpSession session,Double lat,Double lng){
        Page page = pageRepository.findById(pageId).orElseThrow(() -> new IllegalStateException("페이지 없음"));
        //user 정보 추출
        User user = getUser(principal, session,lat,lng);

        RoleType roleType = user.getRoleType();

        //해당 메뉴가 접근가능한지 확인
        validationAccessPage(roleType,page.getRoleType());

        //meta 1 -> top_nav 메타정보 가져오기
        TopNavMeta topNavMeta = getTopNavMeta(page.getId(), user);

        //meta 2 -> 페이지 랜더링할 메타정보 가져오기
        PageMeta pageMeta = getPageMeta(page,roleType);

        //meta 3 -> 권한 정보 관련 메타 가져오기
        PermissionMeta permissionMeta =permissionMetaAssembler.assemble(user.getLoginId(),roleType);

        //전체 response 객체 셋팅해서 조립
        return new PageContextResponse(topNavMeta, pageMeta, permissionMeta);
    }

    /**
     * User 정보 셋팅(로그인/비로그인 시 )
     * @param principal
     * @param session
     * @param lat
     * @param lng
     * @return
     */
    private User getUser(CustomUserDetail principal, HttpSession session,Double lat,Double lng) {
        //CASE 1 ) 로그인 시 user 정보 가져옴
        if(principal !=null){
            return userService.findById(principal.getId());
        }

        //CASE 2) 로그인 x
        //위도, 경도 정보가 없으면 현위치 기반으로 뽑아온다.
        String GUEST_ADDRESS = "GUEST_ADDRESS";
        if(lat != null && lng !=null){
            Address guestAddress = googleMapService.convertAddressFromGoogleApi(lat, lng);
            session.setAttribute(GUEST_ADDRESS,guestAddress);
            return User.guest(guestAddress);
        }
        //GUEST_ADDRESS 정보가 세션에 저장되어있으면 해당 값을 가져옴
        Address cachedAddress = (Address) session.getAttribute(GUEST_ADDRESS);
        if (cachedAddress == null) {
            return User.guest(Address.getDefaultAddress());
        }
        return User.guest(cachedAddress);
    }

    /**
     * 메뉴가 현 유저가 접근 가능한 메뉴인지 확인
     * @param userRoleType
     * @param pageRoleType
     */
    private void validationAccessPage(RoleType userRoleType, RoleType pageRoleType) {
        if(!userRoleType.canAccess(pageRoleType)){
            throw new IllegalStateException("["+pageRoleType+"]만 접근가능한 메뉴입니다. 현재 나의 권한:["+userRoleType+"]");
        }
    }


    private TopNavMeta getTopNavMeta(UUID pageId, User user) {
        List<TopNavItemMeta> topNavItemMetas = getTopNavItemMetaList(pageId, user);

        if(topNavItemMetas.isEmpty()){
            topNavItemMetas = getGlobalTopNavItems(user);
        }
        return new TopNavMeta(topNavItemMetas);
    }

    private List<TopNavItemMeta> getGlobalTopNavItems(User user){
        return topNavItemRepository.findByPageIdNullAndRoleTypeIn(getAccessibleRoles(user.getRoleType())).stream()
                .map(item->buildTopNavItem(item,user)).toList();
    }

    private List<RoleType> getAccessibleRoles(RoleType roleType){
        return Arrays.stream(RoleType.values()).filter(roleType::canAccess).toList();
    }

    private TopNavItemMeta buildTopNavItem(TopNavItem item, User user) {
        TopNavComponent component = item.getComponent();
        boolean needValue = component.isNeedValue();
        Object value =null;
        if(needValue){
           TopNavProvider topNavProvider = topNavProviderMap.get(component);
           value =  topNavProvider.getValue(user);
        }
        return new TopNavItemMeta(component,needValue,item.getPosition(),value);
    }

    /**
     * PageMeta 가져오기
     * @param page
     * @param userRoleType
     * @return
     */
    private PageMeta getPageMeta(Page page, RoleType userRoleType) {
        List<PageSection> accessiblePageSection = page.getPageSectionList().stream()
                .filter(section -> userRoleType.canAccess(section.getRoleType()))
                .sorted(Comparator.comparing(PageSection::getSortOrder)).toList();

        List<PageSectionMeta> list = getTypeSectionMetas(accessiblePageSection);

        return new PageMeta(list);
    }


    private List<PageSectionMeta> getTypeSectionMetas(List<PageSection> accessiblePageSection) {
        return accessiblePageSection.stream().map(
                pageSection-> new PageSectionMeta(
                        pageSection.getPageSectionType(),
                        pageSection.getActionType(),
                        pageSection.getTitle(),
                        pageSection.getDataSourceType(),
                        pageSection.getJsonConfig(),
                        pageSection.getRouteUrl()))
                .toList();
    }

    /**
     * top_nav 메타데이터 조립
     * @param pageId
     * @param user
     * @return
     */
    private List<TopNavItemMeta> getTopNavItemMetaList(UUID pageId, User user) {
        return pageRepository.findTopNavItems(pageId)
                .stream()
                .filter(item -> user.getRoleType().canAccess(item.roleType()))
                .sorted(Comparator.comparing(TopNavItemDto::position).thenComparing(TopNavItemDto::displayOrder))
                .map(item -> {
                    boolean needValue = TopNavComponent.isNeedValue(item.component());

                    TopNavComponent component = item.component();

                    Object value = null;

                    if(needValue){
                        TopNavProvider topNavProvider = topNavProviderMap.get(component);

                        if (topNavProvider == null) {
                            throw new IllegalStateException(component + " 존재하지 않는 provider");
                        }

                        value = topNavProvider.getValue(user);
                    }

                    return new TopNavItemMeta(component, needValue, item.position(), value);
                }).toList();
    }

}
