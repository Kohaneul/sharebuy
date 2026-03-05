package sharebuy.domain.page.dto;

import org.springframework.stereotype.Component;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.page.dto.PermissionMeta.PermissionItemMeta;

import java.util.Collections;
import java.util.List;

/**
 * 권한 관련 메타정보 셋팅
 */
@Component
public class PermissionMetaAssembler {

    public PermissionMeta assemble(RoleType userRoleType){
        return new PermissionMeta(generatePermission(userRoleType));
    }

    public List<PermissionItemMeta> generatePermission(RoleType roleType){
        PermissionItemMeta permissionItemMeta = PermissionMetaFactory.from(roleType);
        return Collections.singletonList(permissionItemMeta);
    }


    static class PermissionMetaFactory{
        static PermissionItemMeta from(RoleType roleType) {
            return switch (roleType) {
                case ADMIN -> new PermissionItemMeta(true, true, true, true);
                case USER  -> new PermissionItemMeta(true, true, true, false);
                case GUEST -> new PermissionItemMeta(false, false, true, false);
            };
        }
    }

}
