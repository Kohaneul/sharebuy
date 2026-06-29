package sharebuy.domain.menu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.domain.menu.dto.MenuResponse;
import sharebuy.domain.menu.service.MenuService;

import java.util.List;

@RestController
@RequestMapping("/rest/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/all")
    public List<MenuResponse> findAll(@AuthenticationPrincipal CustomUserDetail principal){
        return menuService.findAll(principal);
    }

}
