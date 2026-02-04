package sharebuy.domain.page.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sharebuy.domain.page.dto.CardSectionMeta;
import sharebuy.domain.page.dto.GridSectionMeta;
import sharebuy.domain.page.dto.GridSectionMeta.GridItemMeta;
import sharebuy.domain.page.dto.PageSectionMeta;
import sharebuy.domain.page.dto.TypeSectionMeta;
import sharebuy.domain.page.entity.PageSection;
import sharebuy.domain.page.repository.PageRepository;
import sharebuy.domain.post.type.PageSectionType;
import java.util.List;
import static sharebuy.domain.post.type.PageSectionType.GRID;

@Component
public class GridSectionMetaHandler implements PageSectionMetaHandler{

    @Override
    public PageSectionType getType() {
        return GRID;
    }

    @Override
    public TypeSectionMeta<?> handle(PageSection section) {
        return new GridSectionMeta(fetchGridSectionMeta(section));
    }

    private List<GridItemMeta> fetchGridSectionMeta(PageSection pageSection){
        return null;
    }
}
