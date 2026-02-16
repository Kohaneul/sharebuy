package sharebuy.domain.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sharebuy.domain.post.service.PostService;
import sharebuy.domain.page.dto.PageContextResponse;

import java.util.UUID;

@RestController
@RequestMapping("/board")
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping("/{id}")
    public PageContextResponse test(@PathVariable("id") UUID id){
        return new PageContextResponse(null,null,null);
    }
}
