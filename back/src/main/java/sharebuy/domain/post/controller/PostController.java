package sharebuy.domain.post.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sharebuy.common.payload.CardResponse;
import sharebuy.domain.post.service.PostService;
import sharebuy.domain.page.dto.PageContextResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * 화면 진입 시 취소건 제외한 모든건 가져오기
     * @return
     */
    @GetMapping
    public List<CardResponse> findAll(){
        return postService.findAllData();
    }
}
