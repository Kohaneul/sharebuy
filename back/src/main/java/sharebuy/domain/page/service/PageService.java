package sharebuy.domain.page.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.context.service.UserContextService;
import sharebuy.domain.page.dto.*;
import sharebuy.domain.page.entity.Page;
import sharebuy.domain.page.repository.PageRepository;
import sharebuy.domain.user.entity.User;

import java.util.*;

@Service
public class PageService {

    private final PageRepository pageRepository;
    private final PageSectionService pageSectionService;
    private final PermissionService permissionService;
    private final TopNavService topNavService;
    private final UserContextService userContextService;

    public PageService(PageRepository pageRepository, PageSectionService pageSectionService, PermissionService permissionService, TopNavService topNavService, UserContextService userContextService) {
        this.pageRepository = pageRepository;
        this.pageSectionService = pageSectionService;
        this.permissionService = permissionService;
        this.topNavService = topNavService;
        this.userContextService = userContextService;
    }


    @Transactional(readOnly = true)
    public PageContextResponse getPageContext(UUID pageId, CustomUserDetail principal, HttpSession session,Map<String,String> paramMap){
        Page page = pageRepository.findById(pageId).orElseThrow(() -> new IllegalStateException("페이지 없음"));

        ContextParam contextParam = new ContextParam(paramMap);
        Double lat = contextParam.getLat();
        Double lng = contextParam.getLng();

        //user 정보 추출
        User user = userContextService.getUser(principal, session,lat,lng);

        RoleType roleType = user.getRoleType();

        //해당 메뉴가 접근가능한지 확인
        validationAccessPage(roleType,page.getRoleType());

        //meta 1 -> top_nav 메타정보 가져오기
        TopNavMeta topNavMeta = topNavService.getTopNavMeta(page.getId(), user);

        //meta 2 -> 페이지 랜더링할 메타정보 가져오기
        PageMeta pageMeta = pageSectionService.getPageMeta(page,paramMap,roleType);

        //meta 3 -> 권한 정보 관련 메타 가져오기
        PermissionMeta permissionMeta =permissionService.permissionMeta(user.getLoginId(),roleType);

        //전체 response 객체 셋팅해서 조립
        return new PageContextResponse(topNavMeta, pageMeta, permissionMeta);
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




}
