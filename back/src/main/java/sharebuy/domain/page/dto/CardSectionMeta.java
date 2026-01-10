package sharebuy.domain.page.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.post.type.PageType.CARD;

public final class CardSectionMeta implements PageMeta<CardSectionMeta.CardItemMeta> {

    @Override
    public PageType type() {
        return CARD;
    }

    @Override
    public List<CardItemMeta> items() {
        return List.of();
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
