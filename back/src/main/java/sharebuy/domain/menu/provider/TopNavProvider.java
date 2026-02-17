package sharebuy.domain.menu.provider;

import jakarta.annotation.Nullable;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.user.entity.User;

public sealed interface TopNavProvider permits LocationInfoProvider,AlarmProvider{
    TopNavComponent getType();

    Object getValue(@Nullable User user, Menu menu);
}
