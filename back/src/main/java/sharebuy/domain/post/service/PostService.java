package sharebuy.domain.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sharebuy.common.payload.CardResponse;
import sharebuy.common.payload.CardResponse.AuthorInfo;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.post.repository.PostRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static sharebuy.domain.post.domain.PostStatus.CANCELED;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<CardResponse> findAllData(){
        return postRepository.findByStatusNot(CANCELED)
                .stream()
                .map(post -> new CardResponse(
                        post.getId(),
                        post.getTitle(),
                        new AuthorInfo(post.getUser().getNickname(), post.getUser().getAvatar()),
                        post.getContent(),
                        post.getImgUrl().isEmpty() ? null : post.getImgUrl().get(0),
                        post.getStatus().name()
                ))
                .toList();
    }

    private Post findById(UUID id){
        return postRepository.findById(id).orElseThrow(()->new IllegalStateException("존재하지 않습니다."));
    }
}
