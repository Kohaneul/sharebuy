package sharebuy.common.payload;

import org.springframework.util.CollectionUtils;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.user.entity.User;

import java.util.List;
import java.util.UUID;
public record CardResponse(
        UUID id,
        String title,
        String nickName,
        String avatar,
        String content,
        String imgUrl,
        String status
){
    public static CardResponse from(Post post){
        User user = post.getUser();
        return new CardResponse(post.getId(),post.getTitle(),user.getNickname(),user.getAvatar(),post.getContent(),
                getThumbnail(post),post.getStatus().name());
    }

    private static String getThumbnail(Post post) {
        List<String> list = post.getImgUrl();
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }
}