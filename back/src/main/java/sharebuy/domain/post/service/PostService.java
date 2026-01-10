package sharebuy.domain.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.post.repository.PostRepository;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    private Post findById(UUID id){
        return postRepository.findById(id).orElseThrow(()->new IllegalStateException("존재하지 않습니다."));
    }
}
