package sharebuy.domain.page.dto;

public record PageContextResponse (
    TopNavMeta topNav,
    PageMeta pageMeta,
    PermissionMeta permission){}
