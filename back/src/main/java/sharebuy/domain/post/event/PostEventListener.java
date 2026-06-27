package sharebuy.domain.post.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sharebuy.domain.post.service.PostService;

@Component
@RequiredArgsConstructor
public class PostEventListener {
    private final PostService postService;

    @EventListener
    public void onPostFull(PostEndEvent postEndEvent){
        postService.orderEndAuto(postEndEvent.getPostId());
    }
}
