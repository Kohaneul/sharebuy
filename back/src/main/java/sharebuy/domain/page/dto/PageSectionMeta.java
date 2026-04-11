package sharebuy.domain.page.dto;

import sharebuy.common.domain.ActionType;
import sharebuy.domain.post.type.PageSectionType;

public record PageSectionMeta (
    PageSectionType type,
    ActionType actionType,
    String title,
    String dataUrl,
    String jsonConfig
){};
