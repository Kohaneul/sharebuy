package sharebuy.domain.page.service;

import org.springframework.stereotype.Service;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.page.dto.PermissionMeta;
import sharebuy.domain.page.dto.PermissionMetaAssembler;

@Service
public class PermissionService {
    private final PermissionMetaAssembler permissionMetaAssembler;

    public PermissionService(PermissionMetaAssembler permissionMetaAssembler) {
        this.permissionMetaAssembler = permissionMetaAssembler;
    }
    public PermissionMeta permissionMeta(String loginId, RoleType roleType){
        return permissionMetaAssembler.assemble(loginId,roleType);
    }
}
