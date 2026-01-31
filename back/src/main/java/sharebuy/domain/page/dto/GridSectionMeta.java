package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sharebuy.domain.post.type.GridType;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.post.type.PageType.GRID;

public final class GridSectionMeta implements PageSectionMeta<GridSectionMeta.GridItemMeta> {

    private final List<GridItemMeta> gridItemMetaList;

    public GridSectionMeta(List<GridItemMeta> gridItemMetaList) {
        this.gridItemMetaList = gridItemMetaList;
    }

    @Override
    public PageType type() {
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
