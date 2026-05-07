package sharebuy.domain.page.dto;

import sharebuy.common.domain.RoleType;
import sharebuy.domain.page.domain.TopNavComponent;
import sharebuy.domain.page.domain.Position;

public record TopNavItemDto(
        RoleType roleType,
        TopNavComponent component,
        Integer displayOrder,
        Position position
){}
