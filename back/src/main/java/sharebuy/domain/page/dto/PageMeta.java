package sharebuy.domain.page.dto;

import sharebuy.domain.post.type.PageType;

import java.util.List;

public sealed interface PageMeta<T>
        permits CardSectionMeta, GridSectionMeta, InputSectionMeta, PermissionMeta, SearchFormSectionMeta, TopNavMeta {

    PageType type();
    List<T> items();
}
