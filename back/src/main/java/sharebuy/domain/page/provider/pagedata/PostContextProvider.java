package sharebuy.domain.page.provider.pagedata;

import org.springframework.stereotype.Component;
import sharebuy.domain.page.domain.DataSourceType;
import sharebuy.domain.post.service.PostService;

import java.util.Map;
import java.util.UUID;

import static sharebuy.domain.page.domain.DataSourceType.POST;

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
    public Object get(Map<String, Object> params) {
        String actionType = String.valueOf(params.get(ContextConstants.ACTION_TYPE));
        return switch (actionType) {
            case "ALL" ->
                    postService.findAllData((double) params.get(ContextConstants.LAT), (double) params.get(ContextConstants.LNG));
            case "DETAIL" -> postService.findById((UUID) params.get(ContextConstants.ID));
            default -> throw new IllegalStateException("Unexpected value: " + actionType);
        };
    }
}