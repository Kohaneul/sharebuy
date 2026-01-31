package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sharebuy.domain.post.type.InputType;
import sharebuy.domain.post.type.PageSectionType;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.page.dto.SearchFormSectionMeta.*;
import static sharebuy.domain.post.type.PageSectionType.SEARCH_FORM;
import static sharebuy.domain.post.type.PageType.PAGE;

public final class SearchFormSectionMeta implements PageSectionMeta<SearchFormItemMeta> {
    private final List<SearchFormItemMeta> searchFormItemList;

    public SearchFormSectionMeta(List<SearchFormItemMeta> searchFormItemList) {
        this.searchFormItemList = searchFormItemList;
    }


    @Override
    public PageType type() {
        return PAGE;
    }

    @Override
    public PageSectionType pageSectionType() {
        return SEARCH_FORM;
    }

    @Override
    public List<SearchFormItemMeta> items() {
        return searchFormItemList;
    }

    @AllArgsConstructor
    @Getter
    public static class SearchFormItemMeta {
        private int order;
        private String label;
        private InputType type;
        private boolean readOnly;
    }

}
