package sharebuy.domain.menu.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.repository.UserRepository;

import static sharebuy.common.domain.RoleType.GUEST;
import static sharebuy.domain.menu.domain.TopNavComponent.LOCATION_INFO;
@Component
public final class LocationInfoProvider implements TopNavProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public TopNavComponent getType() {
        return LOCATION_INFO;
    }

    @Override
    public Object getValue(User user, Menu menu) {
        RoleType roleType = (user != null) ? user.getRoleType() : GUEST;

        if(roleType.canAccess(menu.getRoleType())){
            Address address = user.getAddress();
            return address != null ? address.getPrimaryAddress() : null;
        }
        return "";
    }


}
