package sharebuy.domain.page.service;

import org.springframework.stereotype.Service;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.page.domain.DataSourceType;
import sharebuy.domain.page.domain.Position;
import sharebuy.domain.page.dto.PageMeta;
import sharebuy.domain.page.dto.PageSectionMeta;
import sharebuy.domain.page.dto.UserContextParam;
import sharebuy.domain.page.entity.Page;
import sharebuy.domain.page.entity.PageSection;
import sharebuy.domain.page.provider.pagedata.PageContextProvider;
import sharebuy.domain.page.repository.PageSectionRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PageSectionService {
    private final PageSectionRepository pageSectionRepository;
    private final List<PageContextProvider> pageContextProviders;
    private final Map<DataSourceType, PageContextProvider> pageContextProviderMap;

    public PageSectionService(PageSectionRepository pageSectionRepository, List<PageContextProvider> pageContextProviders, Map<DataSourceType, PageContextProvider> pageContextProviderMap) {
        this.pageSectionRepository = pageSectionRepository;
        this.pageContextProviders = pageContextProviders;
        this.pageContextProviderMap = pageContextProviders.stream().collect(Collectors.toMap(PageContextProvider::getType,Function.identity()));
    }


    /**
     * PageMeta 가져오기
     * @param page
     * @param userRoleType
     * @return
     */
    public PageMeta getPageMeta(Page page, UserContextParam userContextParam, RoleType userRoleType) {
        List<RoleType> accessibleRoles = userRoleType.getAccessibleRoles();

        List<PageSection> accessiblePageSection = pageSectionRepository.findByPageIdAndRoleTypeIn(page.getId(),accessibleRoles).stream()
                .sorted(Comparator.comparing(PageSection::getSortOrder)).toList();

        List<PageSectionMeta> list = getTypeSectionMetas(accessiblePageSection,userContextParam);

        return new PageMeta(list);
    }


    private Object assemble(DataSourceType dataSourceType,UserContextParam userContextParam,String actionParam){
        PageContextProvider pageContextProvider = pageContextProviderMap.get(dataSourceType);
       return pageContextProvider.get(userContextParam,actionParam);
    }

    private List<PageSectionMeta> getTypeSectionMetas(List<PageSection> accessiblePageSection,UserContextParam userContextParam) {
        return accessiblePageSection.stream().map(
                        pageSection-> new PageSectionMeta(
                                pageSection.getPageSectionType(),
                                pageSection.getActionType(),
                                pageSection.getTitle(),
                                pageSection.getDataSourceType(),
                                pageSection.getJsonConfig(),
                                pageSection.getRouteUrl(),
                                pageSection.getMappingKey(),
                                Objects.isNull(pageSection.getPosition())? Position.LEFT:pageSection.getPosition(),
                                !Objects.isNull(pageSection.getDataSourceType()) ?
                                        assemble(pageSection.getDataSourceType(),userContextParam, pageSection.getActionParam()) : null)
                )
                .toList();
    }

}
