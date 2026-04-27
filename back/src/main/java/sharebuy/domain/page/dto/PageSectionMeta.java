package sharebuy.domain.page.dto;

import sharebuy.common.domain.ActionType;
import sharebuy.domain.page.domain.DataSourceType;
import sharebuy.domain.post.type.PageSectionType;

public record PageSectionMeta (
    PageSectionType type,
    ActionType actionType,
    String title,
    DataSourceType dataSourceType,
    String jsonConfig,
    String routeUrl,
    Object value
){};
