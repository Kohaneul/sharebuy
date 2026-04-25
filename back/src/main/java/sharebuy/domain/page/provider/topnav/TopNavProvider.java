package sharebuy.domain.page.provider.topnav;

import jakarta.annotation.Nullable;
import sharebuy.domain.page.domain.TopNavComponent;
import sharebuy.domain.user.entity.User;

public sealed interface TopNavProvider permits LocationInfoProvider, AlarmProvider {
    TopNavComponent getType();

    Object getValue(@Nullable User user);
}
