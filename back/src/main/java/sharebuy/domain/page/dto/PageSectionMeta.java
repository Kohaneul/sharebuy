package sharebuy.domain.page.dto;

import sharebuy.domain.post.type.PageSectionType;

import java.util.Map;

public record PageSectionMeta (
    PageSectionType type,
    String dataUrl
){};
