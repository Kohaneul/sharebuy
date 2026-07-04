package sharebuy.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.domain.menu.entity.Menu;

import java.util.UUID;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuChildResponse{
   private UUID id;
   private String title;
   private String routeName;

    public static MenuChildResponse from(Menu menu){
        return new MenuChildResponse(menu.getId(),menu.getTitle(),menu.getRouteName());
    }
};