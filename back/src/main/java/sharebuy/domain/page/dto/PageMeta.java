package sharebuy.domain.page.dto;

import lombok.RequiredArgsConstructor;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.post.type.PageType.PAGE;
@RequiredArgsConstructor
public final class PageMeta implements UiMeta{

    private final List<PageSectionMeta<?>> pageItemMetaList;

    @Override
    public PageType type() {
        return PAGE;
    }

    @Override
    public List<?> items() {
        return pageItemMetaList;
    }


}
