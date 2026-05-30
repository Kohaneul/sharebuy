package sharebuy.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.domain.RoleType;
import sharebuy.common.entity.BaseResponse;
import sharebuy.common.exception.ShareBuyException;
import sharebuy.common.payload.CardResponse;
import sharebuy.domain.post.domain.ParticipationStatus;
import sharebuy.domain.post.dto.PostDetailResponse;
import sharebuy.domain.post.entity.Participation;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.post.repository.ParticipationRepository;
import sharebuy.domain.post.repository.PostRepository;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static sharebuy.common.exception.ErrorCode.*;
import static sharebuy.domain.post.policy.PostPolicy.DEFAULT_RADIUS_KM;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, ParticipationRepository participationRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.participationRepository = participationRepository;
        this.userRepository = userRepository;
    }


    @Transactional(readOnly = true)
    public List<CardResponse> findAllData(double latitude, double longitude){
        return postRepository.findNearbyPosts(latitude, longitude, DEFAULT_RADIUS_KM);
    }

    @Transactional(readOnly = true)
    public PostDetailResponse findById(UUID id, CustomUserDetail principal){
        Post post = postRepository.findByIdWithImages(id).orElseThrow(() -> new IllegalStateException("존재하지 않습니다."));
        boolean canClose = false;
        if(principal != null){
            UUID userId = principal.getId();
            UUID postOwnerId = post.getUser().getId();

            canClose = userId.equals(postOwnerId) || principal.getRoleType()== RoleType.ADMIN;
        }
        return PostDetailResponse.from(post,canClose);
    }

    /**
     * 참여
     * @param postId
     * @param userId
     * @return
     */
    @Transactional
    public BaseResponse participate(UUID postId, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ShareBuyException(USER_NOT_FOUND));
        user.validateUserActive();

        Post post = postRepository.findById(postId).orElseThrow(() -> new ShareBuyException(POST_NOT_FOUND));
        post.validateCanParticipate(user);
        existsByPostIdAndUserId(user,post);

       int updatedRow = postRepository.participate(postId);
       if(updatedRow>0){
           Participation participation = new Participation(UUID.randomUUID(),post,user,0, LocalDateTime.now(), ParticipationStatus.JOINED);
           participationRepository.save(participation);
           return new BaseResponse(true);
       }
        throw new ShareBuyException(SOLD_OUT);
    }


    private void existsByPostIdAndUserId(User user, Post post){
        //이미 등록한 상황이라면
        if (participationRepository.existsByPostIdAndUserId(post.getId(), user.getId())) {
            throw new ShareBuyException(ALREADY_PARTICIPATED);
        }

    }

    public PostDetailResponse addPost(CustomUserDetail principal) {
        return null;
    }
}
