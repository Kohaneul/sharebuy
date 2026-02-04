package sharebuy.domain.page.handler;

import sharebuy.domain.page.dto.TypeSectionMeta;
import sharebuy.domain.page.entity.PageSection;
import sharebuy.domain.post.type.PageSectionType;

public interface PageSectionMetaHandler {
    PageSectionType getType();
    TypeSectionMeta<?> handle(PageSection section);
}
