package sharebuy.domain.page.provider.pagedata;

import org.springframework.stereotype.Component;
import sharebuy.domain.page.domain.DataSourceType;
import sharebuy.domain.page.dto.UserContextParam;
import sharebuy.domain.post.service.PostService;

import java.util.Map;

import static sharebuy.domain.page.domain.DataSourceType.POST;
import static sharebuy.domain.page.provider.pagedata.ContextConstants.ID;

@Component
public class PostContextProvider implements PageContextProvider {

    private final PostService postService;

    public PostContextProvider(PostService postService) {
        this.postService = postService;
    }

    @Override
    public DataSourceType getType() {
        return POST;
    }

    @Override
    public Object get(UserContextParam userContextParam,String actionParam) {

        return switch (actionParam) {
            case "ALL" ->
                    postService.findAllData(userContextParam.getLat(), userContextParam.getLng());
            case "DETAIL" -> postService.findById(userContextParam.getUUID(ID));
            default ->null;
        };
    }
}