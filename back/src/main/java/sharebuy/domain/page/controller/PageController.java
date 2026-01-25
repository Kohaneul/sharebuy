package sharebuy.domain.page.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sharebuy.domain.page.dto.PageContextResponse;
import sharebuy.domain.page.service.PageService;

import java.util.UUID;

@RestController
@RequestMapping("/rest/page")
@RequiredArgsConstructor
public class PageController {
    private final PageService pageService;

    @GetMapping("/{menuId}")
    public PageContextResponse page(@PathVariable("menuId") UUID menuId,@AuthenticationPrincipal(expression = "id") UUID id){
        return pageService.findById(menuId,id);
    }

}
