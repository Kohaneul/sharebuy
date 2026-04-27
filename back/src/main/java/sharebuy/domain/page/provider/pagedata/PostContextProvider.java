package sharebuy.domain.page.provider.pagedata;

import org.springframework.stereotype.Component;
import sharebuy.domain.page.domain.DataSourceType;
import sharebuy.domain.page.dto.ContextParam;
import sharebuy.domain.post.service.PostService;

import java.util.Map;
import java.util.UUID;

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
    public Object get(Map<String, String> params) {
        String actionType = String.valueOf(params.get(ContextConstants.ACTION_TYPE));
        ContextParam contextParam = new ContextParam(params);

        return switch (actionType) {
            case "ALL" ->
                    postService.findAllData(contextParam.getLat(), contextParam.getLng());
            case "DETAIL" -> postService.findById(contextParam.getUUID(ID));
            default -> throw new IllegalStateException("Unexpected value: " + actionType);
        };
    }
}