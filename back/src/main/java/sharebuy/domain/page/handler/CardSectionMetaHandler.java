package sharebuy.domain.page.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sharebuy.domain.page.dto.CardSectionMeta;
import sharebuy.domain.page.dto.CardSectionMeta.CardItemMeta;
import sharebuy.domain.page.dto.GridSectionMeta;
import sharebuy.domain.page.dto.PageSectionMeta;
import sharebuy.domain.page.dto.TypeSectionMeta;
import sharebuy.domain.page.entity.PageSection;
import sharebuy.domain.post.type.PageSectionType;

import java.util.List;

import static sharebuy.domain.post.type.PageSectionType.CARD;

@RequiredArgsConstructor
@Component
public class CardSectionMetaHandler implements PageSectionMetaHandler {

    @Override
    public PageSectionType getType() {
        return CARD;
    }

    @Override
    public TypeSectionMeta<?> handle(PageSection section) {
        return new CardSectionMeta(fetchCardSectionMeta(section));
    }

    private List<CardItemMeta> fetchCardSectionMeta(PageSection pageSection){
        return null;
    }

}
