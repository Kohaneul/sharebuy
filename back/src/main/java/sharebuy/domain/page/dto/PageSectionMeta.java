package sharebuy.domain.page.dto;

import sharebuy.domain.post.type.PageSectionType;

public record PageSectionMeta (
    PageSectionType type,
    String title,
    String dataUrl
){};
