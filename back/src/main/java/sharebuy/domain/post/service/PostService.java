package sharebuy.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sharebuy.common.auth.config.CustomUserDetail;
import sharebuy.common.domain.RoleType;
import sharebuy.common.entity.BaseResponse;
import sharebuy.common.exception.ShareBuyException;
import sharebuy.common.payload.CardResponse;
import sharebuy.domain.post.domain.ParticipationStatus;
import sharebuy.domain.post.domain.PostStatus;
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
           Participation participation = new Participation(UUID.randomUUID(),post,user,post.getPerPrice(), LocalDateTime.now(), ParticipationStatus.JOINED);
           participationRepository.save(participation);
           return new BaseResponse(true,null);
       }
        throw new ShareBuyException(SOLD_OUT);
    }

    /**
     * 주문 마감
     * @param postId
     * @param userId
     * @return
     */
    @Transactional
    public BaseResponse orderEnd(UUID postId,UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ShareBuyException(USER_NOT_FOUND));
        Post post = postRepository.findById(postId).orElseThrow(() -> new ShareBuyException(POST_NOT_FOUND));
        //작성자가 아닌 유저가 마감처리 할 경우 throw
        post.validateOwnerUser(user);

        //1.해당 게시글의 상태값 변경
        int updatedRow = postRepository.changeStatus(post.getId(), PostStatus.CLOSED);

        if(updatedRow>0){
          //@TODO 주문 마감 시 사용자에게 메시지 or 카카오톡 알림 전송(eventListener)
        }
        return new BaseResponse(true,null);
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
