package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.post.type.PageType.PERMISSION;
@RequiredArgsConstructor
@Getter
public final class PermissionMeta implements UiMeta {
    private final List<PermissionItemMeta> permissionItemMetaList;

    @Override
    public PageType type() {
        return PERMISSION;
    }

    @Override
    public List<PermissionItemMeta> items() {
        return permissionItemMetaList;
    }

    @Getter
    @AllArgsConstructor
    static class PermissionItemMeta {
        private boolean create;
        private boolean write;
        private boolean read;
        private boolean delete;
    }

}
