package sharebuy.domain.page.dto;

public record PageContextResponse (
    TopNavMeta topNavMeta,
    PageMeta pageMeta,
    PermissionMeta permissionMeta){}
