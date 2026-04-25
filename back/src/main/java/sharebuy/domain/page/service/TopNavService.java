package sharebuy.domain.page.service;

import org.springframework.stereotype.Service;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.page.domain.TopNavComponent;
import sharebuy.domain.page.dto.TopNavItemDto;
import sharebuy.domain.page.dto.TopNavMeta;
import sharebuy.domain.page.dto.TopNavMeta.TopNavItemMeta;
import sharebuy.domain.page.entity.TopNavItem;
import sharebuy.domain.page.provider.topnav.TopNavProvider;
import sharebuy.domain.page.repository.TopNavItemRepository;
import sharebuy.domain.user.entity.User;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TopNavService {
    private final TopNavItemRepository topNavItemRepository;
    private final Map<TopNavComponent, TopNavProvider> topNavProviderMap;
    private final List<TopNavProvider> topNavProviders;

    public TopNavService(TopNavItemRepository topNavItemRepository, Map<TopNavComponent, TopNavProvider> topNavProviderMap, List<TopNavProvider> topNavProviders) {
        this.topNavItemRepository = topNavItemRepository;
        this.topNavProviderMap = topNavProviders.stream().collect(Collectors.toMap(TopNavProvider::getType, Function.identity()));
        this.topNavProviders = topNavProviders;
    }


    public TopNavMeta getTopNavMeta(UUID pageId, User user) {
        List<TopNavItemMeta> topNavItemMetas = getTopNavItemMetaList(pageId, user);

        if(topNavItemMetas.isEmpty()){
            topNavItemMetas = getGlobalTopNavItems(user);
        }
        return new TopNavMeta(topNavItemMetas);
    }

    private List<TopNavItemMeta> getGlobalTopNavItems(User user){
        return topNavItemRepository.findByPageIdNullAndRoleTypeIn(user.getRoleType().getAccessibleRoles()).stream()
                .map(item->buildTopNavItem(item,user)).toList();
    }


    /**
     * topNav default 셋팅
     * @param item
     * @param user
     * @return
     */
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
     * top_nav 메타데이터 조립
     * @param pageId
     * @param user
     * @return
     */
    private List<TopNavItemMeta> getTopNavItemMetaList(UUID pageId, User user) {
        return topNavItemRepository.findTopNavItems(pageId)
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
