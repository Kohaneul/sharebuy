package sharebuy.common.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.domain.RoleType;

@Service
public class AuthorizationService {

    public boolean canAccess(Authentication authentication,RoleType requiredRole){
        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        RoleType userRoleType = user.getRoleType();

        return userRoleType.canAccess(requiredRole);
    }
}
