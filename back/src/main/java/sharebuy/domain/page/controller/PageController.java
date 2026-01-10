package sharebuy.domain.page.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sharebuy.domain.menu.service.MenuService;
import sharebuy.domain.page.dto.PageContextResponse;
import sharebuy.domain.page.service.PageService;

import java.util.UUID;

@RestController
@RequestMapping("/rest/page")
public class PageController {
    @Autowired
    private PageService pageService;

    @GetMapping("/{menuId}/{userId}")
    public PageContextResponse page(@PathVariable("menuId") UUID menuId,@PathVariable("userId") UUID userId){
        return pageService.findById(menuId,userId);
    }

}
