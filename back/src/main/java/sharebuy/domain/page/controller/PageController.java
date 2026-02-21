package sharebuy.domain.page.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.domain.page.dto.PageContextResponse;
import sharebuy.domain.page.service.PageService;

import java.util.UUID;

@RestController
@RequestMapping("/rest/page")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @GetMapping("/{menuId}")
    public PageContextResponse page(@PathVariable("menuId") UUID menuId,
                                    @AuthenticationPrincipal CustomUserDetail principal,
                                    HttpSession session,
                                    @RequestParam(required = false,name = "lat")Double lat,
                                    @RequestParam(required = false,name = "lng")Double lng){
        return pageService.getPageContext(menuId,principal,session,lat,lng);
    }


}
