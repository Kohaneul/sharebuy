package sharebuy.domain.page.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.domain.page.dto.PageContextResponse;
import sharebuy.domain.page.dto.TopNavMeta;
import sharebuy.domain.page.service.PageService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rest/page")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @GetMapping("/{pageId}")
    public PageContextResponse page(@PathVariable("pageId") UUID pageId,
                                    @AuthenticationPrincipal CustomUserDetail principal,
                                    HttpSession session,
                                    @RequestParam(required = false) Map<String,String> param){
                return pageService.getPageContext(pageId,principal,session,param);
    }

    @GetMapping("/default/top_nav")
    public TopNavMeta getDetaultTopNavMeta(
            @AuthenticationPrincipal CustomUserDetail principal,
            HttpSession session,
            @RequestParam(required = false) Map<String,String> param){
        return pageService.getDetaultTopNavMeta(principal,session,param);
    }

}
