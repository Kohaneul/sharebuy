package sharebuy.domain.page.dto;

import sharebuy.domain.post.type.PageType;

import java.util.List;

public sealed interface PageSectionMeta<T> extends UiMeta
        permits CardSectionMeta, GridSectionMeta, InputSectionMeta,  SearchFormSectionMeta {

    PageType type();
    List<T> items();
}
