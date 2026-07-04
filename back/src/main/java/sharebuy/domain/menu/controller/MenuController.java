package sharebuy.domain.menu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.domain.RoleType;
import sharebuy.domain.menu.dto.MenuResponse;
import sharebuy.domain.menu.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/rest/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/all")
    public Object findAll(@RequestParam("roleType") RoleType roleType){
        return menuService.findAll(roleType);
    }

}
