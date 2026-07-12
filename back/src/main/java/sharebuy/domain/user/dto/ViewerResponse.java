package sharebuy.domain.user.dto;

import sharebuy.common.domain.RoleType;

public record ViewerResponse(
        String loginId,
        RoleType roleType,
        Double latitude,
        Double longitude
) {}