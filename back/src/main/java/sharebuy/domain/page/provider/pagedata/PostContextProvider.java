package sharebuy.domain.page.provider.pagedata;

import org.springframework.stereotype.Component;
import sharebuy.domain.page.domain.DataSourceType;
import sharebuy.domain.page.dto.UserContextParam;
import sharebuy.domain.post.repository.PostRepository;

import static sharebuy.domain.page.domain.DataSourceType.POST;
import static sharebuy.domain.page.provider.pagedata.ContextConstants.ID;
import static sharebuy.domain.post.policy.PostPolicy.DEFAULT_RADIUS_KM;

@Component
public class PostContextProvider implements PageContextProvider {

    private final PostRepository postRepository;

    public PostContextProvider(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public DataSourceType getType() {
        return POST;
    }

    @Override
    public Object get(UserContextParam userContextParam,String actionParam) {

        return switch (actionParam) {
            case "ALL" ->
                    postRepository.findNearbyPosts(userContextParam.getLat(), userContextParam.getLng(),DEFAULT_RADIUS_KM);
            case "DETAIL" -> postRepository.findByIdWithImages(userContextParam.getUUID(ID));
            default ->null;
        };
    }
}