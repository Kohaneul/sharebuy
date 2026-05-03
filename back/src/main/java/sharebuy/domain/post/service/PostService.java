package sharebuy.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sharebuy.common.payload.CardResponse;
import sharebuy.domain.post.dto.PostDetailResponse;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.post.repository.PostRepository;

import java.util.List;
import java.util.UUID;
import static sharebuy.domain.post.policy.PostPolicy.DEFAULT_RADIUS_KM;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public List<CardResponse> findAllData(double latitude, double longitude){
        return postRepository.findNearbyPosts(latitude, longitude, DEFAULT_RADIUS_KM);
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findById(UUID id){
        Post post = postRepository.findByIdWithImages(id).orElseThrow(() -> new IllegalStateException("존재하지 않습니다."));
        return PostDetailResponse.from(post);
    }
}
