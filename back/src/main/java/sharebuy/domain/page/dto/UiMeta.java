package sharebuy.domain.page.dto;

import sharebuy.domain.post.type.PageType;

import java.util.List;

public sealed interface UiMeta
        permits PageMeta, PageSectionMeta, PermissionMeta, TopNavMeta {

    PageType type();
    List<?> items();
}
