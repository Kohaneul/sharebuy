package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharebuy.domain.post.type.GridType;
import sharebuy.domain.post.type.PageSectionType;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.page.dto.GridSectionMeta.*;
import static sharebuy.domain.post.type.PageSectionType.GRID;
import static sharebuy.domain.post.type.PageType.PAGE;

@RequiredArgsConstructor
public final class GridSectionMeta implements TypeSectionMeta<GridItemMeta> {

    private final List<GridItemMeta> gridItemMetaList;


    @Override
    public PageType getPageType() {
        return PAGE;
    }

    @Override
    public PageSectionType pageSectionType() {
        return GRID;
    }

    @Override
    public List<GridItemMeta> items() {
        return gridItemMetaList;
    }

    @AllArgsConstructor
    @Getter
    public static class GridItemMeta {
        private String column;
        private String value;
        private boolean readonly;
        private GridType gridType;
    }

}
