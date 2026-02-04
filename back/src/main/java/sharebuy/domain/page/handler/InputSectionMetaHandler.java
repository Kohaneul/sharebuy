package sharebuy.domain.page.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sharebuy.domain.page.dto.CardSectionMeta;
import sharebuy.domain.page.dto.CardSectionMeta.CardItemMeta;
import sharebuy.domain.page.dto.InputSectionMeta;
import sharebuy.domain.page.dto.InputSectionMeta.InputItem;
import sharebuy.domain.page.dto.PageSectionMeta;
import sharebuy.domain.page.dto.TypeSectionMeta;
import sharebuy.domain.page.entity.PageSection;
import sharebuy.domain.page.repository.PageRepository;
import sharebuy.domain.post.type.PageSectionType;

import java.util.List;

import static sharebuy.domain.post.type.PageSectionType.CARD;
import static sharebuy.domain.post.type.PageSectionType.INPUT;

@RequiredArgsConstructor
@Component
public class InputSectionMetaHandler implements PageSectionMetaHandler{

    @Override
    public PageSectionType getType() {
        return INPUT;
    }

    @Override
    public TypeSectionMeta<?> handle(PageSection section) {
        return new InputSectionMeta(fetchCardSectionMeta(section));
    }

    private List<InputItem> fetchCardSectionMeta(PageSection pageSection){
        return null;
    }
}
