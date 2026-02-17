package sharebuy.domain.menu.domain;

public enum TopNavComponent {
    LOCATION_INFO(true),
    SEARCH_FORM(false),
    ALARM(true),
    MENU(false);

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


