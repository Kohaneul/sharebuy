package sharebuy.domain.menu.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.menu.repository.MenuRepository;
import sharebuy.domain.user.entity.User;

@Component
public final class MenuProvider implements TopNavProvider {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public TopNavComponent getType() {
        return TopNavComponent.MENU;
    }

    @Override
    public Object getValue(User user, Menu menu) {
        return menuRepository.findMenuByUser(user.getRoleType(),menu.getId());
    }

}
