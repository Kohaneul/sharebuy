package sharebuy.domain.page.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import sharebuy.domain.post.type.PageSectionType;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.page.dto.CardSectionMeta.*;
import static sharebuy.domain.post.type.PageSectionType.CARD;
import static sharebuy.domain.post.type.PageType.PAGE;

public final class CardSectionMeta implements PageSectionMeta<CardItemMeta> {

    private final List<CardItemMeta> cardItemMetaList;

    public CardSectionMeta(List<CardItemMeta> cardItemMetaList) {
        this.cardItemMetaList = cardItemMetaList;
    }

    @Override
    public PageType type() {
        return PAGE;
    }

    @Override
    public PageSectionType pageSectionType() {
        return CARD;
    }

    @Override
    public List<CardItemMeta> items() {
        return cardItemMetaList;
    }


    @AllArgsConstructor
    @Getter
    static class CardItemMeta {
        private String title;
        private String key;
        private String value;
        private int likeQty;
        private int commentQty;
        private String nickName;

    }

}
