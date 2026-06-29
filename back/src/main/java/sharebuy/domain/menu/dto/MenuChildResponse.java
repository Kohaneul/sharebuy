package sharebuy.domain.menu.dto;

import sharebuy.domain.menu.entity.Menu;

import java.util.UUID;

public record MenuChildResponse(
        UUID id,
        String title,
        String routeName) {
    public static MenuChildResponse from(Menu menu){
        return new MenuChildResponse(menu.getId(),menu.getTitle(),menu.getRouteName());
    }

}
