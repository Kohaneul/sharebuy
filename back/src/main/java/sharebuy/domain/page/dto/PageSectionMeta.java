package sharebuy.domain.page.dto;

import sharebuy.domain.post.type.PageSectionType;

import java.util.List;

public sealed interface PageSectionMeta<T> extends UiMeta
        permits CardSectionMeta, GridSectionMeta, InputSectionMeta,  SearchFormSectionMeta {

    PageSectionType pageSectionType();
    List<T> items();
}
