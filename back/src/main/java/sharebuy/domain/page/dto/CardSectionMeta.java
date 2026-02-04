package sharebuy.domain.page.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharebuy.domain.post.type.PageSectionType;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.page.dto.CardSectionMeta.*;
import static sharebuy.domain.post.type.PageSectionType.CARD;
import static sharebuy.domain.post.type.PageType.PAGE;

@RequiredArgsConstructor
public final class CardSectionMeta implements TypeSectionMeta<CardItemMeta> {

    private final List<CardItemMeta> cardItemMetaList;

    @Override
    public List<CardItemMeta> items() {
        return List.of();
    }

    @Override
    public PageType getPageType() {
        return PAGE;
    }

    @Override
    public PageSectionType pageSectionType() {
        return null;
    }


    @AllArgsConstructor
    @Getter
    public static class CardItemMeta {
        private String title;
        private String key;
        private String value;
        private int likeQty;
        private int commentQty;
        private String nickName;

    }

}
