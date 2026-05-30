package sharebuy.domain.post.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.entity.BaseResponse;
import sharebuy.common.payload.CardResponse;
import sharebuy.domain.post.dto.PostDetailResponse;
import sharebuy.domain.post.service.PostService;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 화면 진입 시 취소건 제외한 모든건 가져오기
     * @return
     */
    @GetMapping("/all")
    public List<CardResponse> findAll(@RequestParam("latitude")double latitude,@RequestParam("longitude")double longitude){
        return postService.findAllData(latitude,longitude);
    }

    /**
     * 특정 게시글 클릭했을때
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public PostDetailResponse findById(@PathVariable("id") UUID id){
        return postService.findById(id);
    }

    /**
     * 신규등록
     * @param id
     * @return
     */
    @PostMapping("/add")
    public PostDetailResponse add(@PathVariable("id") UUID id){
        return postService.findById(id);
    }

    @PostMapping("/{postId}/join")
    public BaseResponse participate(@PathVariable("postId") UUID postId
            , @AuthenticationPrincipal CustomUserDetail principal){

        return postService.participate(postId, principal.getId());
    }

}
