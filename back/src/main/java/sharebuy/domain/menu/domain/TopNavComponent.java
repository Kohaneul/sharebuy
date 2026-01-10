package sharebuy.domain.menu.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TopNavComponent {
    LOCATION_INFO(true),
    SEARCH_FORM(false),
    ALARM(true),
    MENU(true);

    private final boolean needValue;

    public boolean isNeedValue() {
        return needValue;
    }

    TopNavComponent(boolean needValue) {
        this.needValue = needValue;
    }

    public static boolean isNeedValue(TopNavComponent topNavComponent) {
        return topNavComponent.isNeedValue();
    }

}


