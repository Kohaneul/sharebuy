package sharebuy.domain.page.dto;

import org.springframework.stereotype.Component;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.page.dto.PermissionMeta.PermissionItemMeta;

import java.util.Collections;
import java.util.List;


@Component
public class PermissionMetaAssembler {

    public PermissionMeta assemble(RoleType userRoleType , Menus menu){
        RoleType menuRoleType = menu.getRoleType();
        if(!userRoleType.canAccess(menuRoleType)){
            throw new IllegalStateException("메뉴 접근 불가");
        }
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
