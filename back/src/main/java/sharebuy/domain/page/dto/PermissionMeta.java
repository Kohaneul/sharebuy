package sharebuy.domain.page.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sharebuy.domain.post.type.PageType;

import java.util.List;

import static sharebuy.domain.post.type.PageType.PERMISSION;

public final class PermissionMeta implements PageMeta<PermissionMeta.PermissionItemMeta> {
    private final List<PermissionItemMeta> permissionMetaList;

    public PermissionMeta(List<PermissionItemMeta> permissionMetaList) {
        this.permissionMetaList = permissionMetaList;
    }

    @Override
    public PageType type() {
        return PERMISSION;
    }

    @Override
    public List<PermissionItemMeta> items() {
        return permissionMetaList;
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
