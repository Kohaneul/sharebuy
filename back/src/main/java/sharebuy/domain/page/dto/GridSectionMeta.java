package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sharebuy.domain.post.type.GridType;
import sharebuy.domain.post.type.PageSectionType;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.page.dto.GridSectionMeta.*;
import static sharebuy.domain.post.type.PageSectionType.GRID;
import static sharebuy.domain.post.type.PageType.PAGE;

public final class GridSectionMeta implements PageSectionMeta<GridItemMeta> {

    private final List<GridItemMeta> gridItemMetaList;

    public GridSectionMeta(List<GridItemMeta> gridItemMetaList) {
        this.gridItemMetaList = gridItemMetaList;
    }

    @Override
    public PageType type() {
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
    static class GridItemMeta {
        private String column;
        private String value;
        private boolean readonly;
        private GridType gridType;
    }

}
