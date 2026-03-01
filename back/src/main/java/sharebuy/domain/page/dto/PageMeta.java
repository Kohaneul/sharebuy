package sharebuy.domain.page.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.post.type.PageType.PAGE;
@RequiredArgsConstructor
@Getter
public final class PageMeta implements UiMeta{

    private final List<PageSectionMeta> pageItemMetaList;

    @Override
    public PageType type() {
        return PAGE;
    }

    @Override
    public List<PageSectionMeta> items() {
        return pageItemMetaList;
    }


}
