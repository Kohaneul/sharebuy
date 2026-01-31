package sharebuy.domain.menu.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.repository.UserRepository;

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
        Address address = userRepository.findByAddress(user.getId());
        return address.getPrimaryAddress()+" "+address.getDetailAddress();
    }


}
