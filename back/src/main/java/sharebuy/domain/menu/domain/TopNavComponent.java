package sharebuy.domain.menu.domain;

public enum TopNavComponent {
    LOCATION_INFO(true),
    SEARCH_FORM(false),
    ALARM(true),
    MENU(true);

    private final boolean needValue;

    TopNavComponent(boolean needValue) {
        this.needValue = needValue;
    }

}


