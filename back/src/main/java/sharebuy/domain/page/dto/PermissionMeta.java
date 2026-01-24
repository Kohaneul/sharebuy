package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.post.type.PageType.PERMISSION;

public final class PermissionMeta implements PageMeta<PermissionMeta.PermissionItemMeta> {
    private final List<PermissionItemMeta> permissionItemMetaList;

    public PermissionMeta(List<PermissionItemMeta> permissionItemMetaList) {
        this.permissionItemMetaList = permissionItemMetaList;
    }


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
