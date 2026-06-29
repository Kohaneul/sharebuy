package sharebuy.domain.menu.dto;

import sharebuy.domain.menu.entity.Menu;

import java.util.List;
import java.util.UUID;

public record MenuResponse(
        UUID id,
        String key,
        String title,
        List<MenuChildResponse> children
) {
    public static MenuResponse from(Menu parent, List<MenuChildResponse> children)
    {
        return new MenuResponse(parent.getId(),parent.getTitle(),parent.getIcon(),children);
    }
}
