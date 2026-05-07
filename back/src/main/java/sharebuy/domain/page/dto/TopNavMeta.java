package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharebuy.domain.page.domain.TopNavComponent;
import sharebuy.domain.page.domain.Position;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.post.type.PageType.TOP_NAV;
@RequiredArgsConstructor
@Getter
public final class TopNavMeta  implements UiMeta{
    private final List<TopNavItemMeta> topNavItemMetaList;

    @Override
    public PageType type() {
        return TOP_NAV;
    }

    @Override
    public List<TopNavItemMeta> items() {
        return topNavItemMetaList;
    }


    @Getter
    @AllArgsConstructor
    public static class TopNavItemMeta{
        private TopNavComponent component;
        private boolean isValue;
        private Position position;
        private Object value;

    }
}
