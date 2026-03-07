package sharebuy.domain.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharebuy.common.payload.CardResponse;
import sharebuy.domain.post.dto.PostDetailResponse;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.post.repository.PostRepository;

import java.util.List;
import java.util.UUID;

import static sharebuy.domain.post.domain.PostStatus.CANCELED;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<CardResponse> findAllData(){
        return postRepository.findByStatusNot(CANCELED)
                .stream()
                .map(CardResponse::from)
                .toList();
    }

    public PostDetailResponse findById(UUID id){
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않습니다."));
        return PostDetailResponse.from(post);
    }
}
